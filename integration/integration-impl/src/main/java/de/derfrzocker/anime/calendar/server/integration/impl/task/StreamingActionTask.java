package de.derfrzocker.anime.calendar.server.integration.impl.task;

import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.core.util.ChangeBuilder;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingTimeLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingUrlLayerTransformer;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Period;
import java.util.List;

@ApplicationScoped
public class StreamingActionTask {

    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    StreamingNotificationActionService actionService;
    @Inject
    AnimeService animeService;

    public void onActionTrigger(@Observes NotificationActionTriggerEvent event) {
        if (!StreamingNotificationAction.NOTIFICATION_ACTION_TYPE.equals(event.action().actionType())) {
            return;
        }

        StreamingNotificationAction action = this.actionService.getById(event.action().id(), event.context())
                                                               .orElseThrow(inconsistentNotFound(event.action().id()));

        this.linkService.createWithData(action.animeId(),
                                        action.integrationId(),
                                        action.integrationAnimeId(),
                                        new AnimeIntegrationLinkCreateData(),
                                        event.context());

        StreamingUrlLayerConfig streamingUrlLayerConfig = new StreamingUrlLayerConfig(StreamingUrlLayerTransformer.LAYER_KEY,
                                                                                      action.integrationId().raw(),
                                                                                      getUrl(action.integrationId(),
                                                                                             action.integrationAnimeId()));
        StreamingTimeLayerConfig streamingTimeLayerConfig = new StreamingTimeLayerConfig(StreamingTimeLayerTransformer.LAYER_KEY,
                                                                                         action.streamingTime(),
                                                                                         Period.ofDays(7),
                                                                                         action.orgEpisodeIndex(),
                                                                                         StreamType.SUB);

        AnimeUpdateData updateData = new AnimeUpdateData(Change.nothing(),
                                                         Change.nothing(),
                                                         ChangeBuilder.<List<LayerStepConfig>>builder()
                                                                      .add(Change.addingToList(new LayerStepConfig(List.of(),
                                                                                                                   streamingUrlLayerConfig)))
                                                                      .add(Change.addingToList(new LayerStepConfig(List.of(),
                                                                                                                   streamingTimeLayerConfig)))
                                                                      .build());
        this.animeService.updateWithData(action.animeId(), updateData, event.context());
    }

    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (IntegrationIds.SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s/time".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.CRUNCHYROLL.equals(integrationId)) {
            return "https://www.crunchyroll.com/series/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }
}
