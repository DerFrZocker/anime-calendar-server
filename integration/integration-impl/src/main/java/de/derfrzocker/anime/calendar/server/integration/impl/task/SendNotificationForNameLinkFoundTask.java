package de.derfrzocker.anime.calendar.server.integration.impl.task;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.service.ManualLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostNameLinkSearchEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationHelperService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SendNotificationForNameLinkFoundTask {

    private static final Logger LOG = Logger.getLogger(SendNotificationForNameLinkFoundTask.class);

    private static final IntegrationId[] INTEGRATION_IDS = new IntegrationId[]{IntegrationIds.ANIDB,
                                                                               IntegrationIds.MY_ANIME_LIST};

    public static final NotificationType NOTIFICATION_TYPE = new NotificationType("NameLink");
    private static final NotificationActionType INTEGRATION_LINK_ACTION_TYPE = new NotificationActionType(
            "IntegrationLink");
    private static final NotificationActionType MANUAL_LINK_ACTION_TYPE = new NotificationActionType("ManualLink");

    @Inject
    IntegrationLinkNotificationActionService integrationActionService;
    @Inject
    ManualLinkNotificationActionService manualLinkActionService;
    @Inject
    NotificationActionService actionService;
    @Inject
    NotificationService notificationService;
    @Inject
    NotificationHelperService helperService;
    @Inject
    AnimeIntegrationLinkService linkService;
    @ConfigProperty(name = "notification.integration-link.valid-length")
    Duration validLength;

    public void onNewNameLink(@Observes PostNameLinkSearchEvent event) {
        List<IntegrationId> integrationIds = new ArrayList<>();

        for (IntegrationId integrationId : INTEGRATION_IDS) {
            if (isNotLinked(event.anime(), integrationId, event.context())) {
                integrationIds.add(integrationId);
            }
        }

        if (integrationIds.isEmpty() && event.searchResults().isEmpty()) {
            LOG.infov("No possible integration option found for anime '{}'.", event.anime().id());
            return;
        }

        Notification notification = createNewNotification(event.context());

        for (Map.Entry<IntegrationId, Collection<NameSearchResult>> entries : event.searchResults().entrySet()) {
            for (NameSearchResult result : entries.getValue()) {
                NotificationAction action = createNewAction(notification.id(),
                                                            INTEGRATION_LINK_ACTION_TYPE,
                                                            false,
                                                            event.context());
                createNewNotificationAction(action.id(), event.anime().id(), result, event.context());
            }
        }

        for (IntegrationId integrationId : integrationIds) {
            NotificationAction action = createNewAction(notification.id(),
                                                        MANUAL_LINK_ACTION_TYPE,
                                                        true,
                                                        event.context());
            createManualLinkAction(action.id(), event.anime(), integrationId, event.context());
        }

        this.helperService.send(notification.id(), event.context());
    }

    private Notification createNewNotification(RequestContext context) {
        NotificationCreateData createData = new NotificationCreateData(NOTIFICATION_TYPE,
                                                                       Instant.now().plus(this.validLength));
        return this.notificationService.createWithData(createData, context);
    }

    private NotificationAction createNewAction(NotificationId id,
                                               NotificationActionType actionType,
                                               boolean requireUserInput,
                                               RequestContext context) {
        NotificationActionCreateData createData = new NotificationActionCreateData(id, actionType, requireUserInput);
        return this.actionService.createWithData(createData, context);
    }

    private void createNewNotificationAction(NotificationActionId actionId,
                                             AnimeId animeId,
                                             NameSearchResult result,
                                             RequestContext context) {
        IntegrationLinkNotificationActionCreateData createData = createData(animeId, result);
        this.integrationActionService.createWithData(actionId, createData, context);
    }

    private IntegrationLinkNotificationActionCreateData createData(AnimeId animeId, NameSearchResult result) {
        return new IntegrationLinkNotificationActionCreateData(animeId,
                                                               result.animeNameHolder().integrationId(),
                                                               result.animeNameHolder().integrationAnimeId(),
                                                               result.score(),
                                                               result.bestName().name());
    }

    private void createManualLinkAction(NotificationActionId id,
                                        Anime anime,
                                        IntegrationId integrationId,
                                        RequestContext context) {
        ManualLinkNotificationActionCreateData createData = new ManualLinkNotificationActionCreateData(anime.id(),
                                                                                                       integrationId);
        this.manualLinkActionService.createWithData(id, createData, context);
    }

    private boolean isNotLinked(Anime anime, IntegrationId integrationId, RequestContext context) {
        return this.linkService.getAllWithId(anime.id(), integrationId, context).findAny().isEmpty();
    }
}
