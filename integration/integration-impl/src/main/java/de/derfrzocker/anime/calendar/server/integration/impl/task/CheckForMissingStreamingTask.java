package de.derfrzocker.anime.calendar.server.integration.impl.task;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationService;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingUrlLayerTransformer;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationHelperService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CheckForMissingStreamingTask {

    private static final Logger LOG = Logger.getLogger(CheckForMissingStreamingTask.class);

    private static final IntegrationId REFERENCE_INTEGRATION_ID = IntegrationIds.MY_ANIME_LIST;

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;
    @Inject
    AnimeIntegrationLinkService animeIntegrationLinkService;
    @Inject
    NotificationService notificationService;
    @Inject
    NotificationActionService notificationActionService;
    @Inject
    StreamingNotificationService streamingNotificationService;
    @Inject
    StreamingNotificationActionService streamingNotificationActionService;
    @Inject
    NotificationHelperService notificationHelperService;
    @ConfigProperty(name = "notification.integration-link.valid-length")
    Duration validLength;

    public Uni<Void> checkForMissingStreaming(RequestContext context) {
        try (Stream<Anime> stream = this.animeService.getAll(context)) {
            List<Anime> animes = stream.toList();
            LOG.infof("Checking for missing streaming in '%d' animes ...", animes.size());

            return Multi.createFrom()
                        .iterable(animes)
                        .emitOn(Infrastructure.getDefaultExecutor())
                        .invoke(anime -> checkForMissingStreaming(anime, context))
                        .collect()
                        .asList()
                        .onFailure()
                        .invoke(throwable -> {
                            LOG.errorf("An error occurred while checking for missing streaming.", throwable);
                        })
                        .invoke(() -> LOG.infof("Finished checking for missing streaming."))
                        .replaceWithVoid();
        }
    }

    private void checkForMissingStreaming(Anime anime, RequestContext context) {
        if (anime.episodeLayers()
                 .stream()
                 .anyMatch(config -> Objects.equals(config.transformConfig().key(),
                                                    StreamingUrlLayerTransformer.LAYER_KEY))) {
            return;
        }

        AnimeOptions options = AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE).build();
        List<Episode> episodes = this.episodeBuilderService.buildEpisodes(anime, options, context);

        Instant startOfWeek = OffsetDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.MIN).toInstant();
        Instant endOfWeek = OffsetDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.MAX).toInstant();
        Episode referenceEpisode = null;
        for (Episode episode : episodes) {
            if (episode.streamingTime() == null) {
                continue;
            }
            if (episode.streamingTime().isBefore(startOfWeek)) {
                continue;
            }
            if (episode.streamingTime().isAfter(endOfWeek)) {
                continue;
            }
            referenceEpisode = episode;
            break;
        }

        if (referenceEpisode == null) {
            return;
        }

        Optional<IntegrationAnimeId> integrationAnimeId = getIntegrationAnimeId(anime, context);
        if (integrationAnimeId.isEmpty()) {
            return;
        }

        Notification notification = createBaseNotification(context);
        createStreamingNotification(notification, anime, referenceEpisode, integrationAnimeId.get(), context);
        NotificationAction action = createBaseAction(notification, context);
        createStreamingAction(action, anime, referenceEpisode.episodeId(), context);
        this.notificationHelperService.send(notification.id(), context);
    }

    private Notification createBaseNotification(RequestContext context) {
        NotificationCreateData createData = new NotificationCreateData(StreamingNotification.NOTIFICATION_TYPE,
                                                                       Instant.now().plus(this.validLength));
        return this.notificationService.createWithData(createData, context);
    }

    private void createStreamingNotification(Notification notification,
                                             Anime anime,
                                             Episode referenceEpisode,
                                             IntegrationAnimeId referenceIntegrationAnimeId,
                                             RequestContext context) {
        StreamingNotificationCreateData createData = new StreamingNotificationCreateData(anime.id(),
                                                                                         referenceEpisode.episodeId(),
                                                                                         referenceEpisode.streamingTime(),
                                                                                         REFERENCE_INTEGRATION_ID,
                                                                                         referenceIntegrationAnimeId);
        this.streamingNotificationService.createWithData(notification.id(), createData, context);
    }

    private NotificationAction createBaseAction(Notification notification, RequestContext context) {
        NotificationActionCreateData createData = new NotificationActionCreateData(notification.id(),
                                                                                   StreamingNotificationAction.NOTIFICATION_ACTION_TYPE,
                                                                                   true);
        return this.notificationActionService.createWithData(createData, context);
    }

    private void createStreamingAction(NotificationAction action,
                                       Anime anime,
                                       int orgEpisodeIndex,
                                       RequestContext context) {
        StreamingNotificationActionCreateData createData = new StreamingNotificationActionCreateData(anime.id(),
                                                                                                     orgEpisodeIndex);
        this.streamingNotificationActionService.createWithData(action.id(), createData, context);
    }

    private Optional<IntegrationAnimeId> getIntegrationAnimeId(Anime anime, RequestContext context) {
        try (Stream<AnimeIntegrationLink> stream = this.animeIntegrationLinkService.getAllWithId(anime.id(),
                                                                                                 REFERENCE_INTEGRATION_ID,
                                                                                                 context)) {

            return stream.findAny().map(AnimeIntegrationLink::integrationAnimeId);
        }
    }
}
