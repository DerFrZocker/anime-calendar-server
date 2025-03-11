package de.derfrzocker.anime.calendar.server.integration.impl.task;

import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
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
import java.util.Collection;
import java.util.Map;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SendNotificationForNameLinkFoundTask {

    public static final NotificationType NOTIFICATION_TYPE = new NotificationType("NameLink");
    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("IntegrationLink");

    @Inject
    IntegrationLinkNotificationActionService integrationActionService;
    @Inject
    NotificationActionService actionService;
    @Inject
    NotificationService notificationService;
    @Inject
    NotificationHelperService helperService;
    @ConfigProperty(name = "notification.integration-link.valid-length")
    Duration validLength;

    public void onNewNameLink(@Observes PostNameLinkSearchEvent event) {
        Notification notification = createNewNotification(event.context());

        for (Map.Entry<IntegrationId, Collection<NameSearchResult>> entries : event.searchResults().entrySet()) {
            for (NameSearchResult result : entries.getValue()) {
                NotificationAction notificationAction = createNewNotificationAction(notification.id(), event.context());
                createNewNotificationAction(notificationAction.id(), event.anime().id(), result, event.context());
            }
        }

        this.helperService.send(notification.id(), event.context());
    }

    private Notification createNewNotification(RequestContext context) {
        NotificationCreateData createData = new NotificationCreateData(NOTIFICATION_TYPE,
                                                                       Instant.now().plus(this.validLength));
        return this.notificationService.createWithData(createData, context);
    }

    private NotificationAction createNewNotificationAction(NotificationId id, RequestContext context) {
        NotificationActionCreateData createData = new NotificationActionCreateData(id, NOTIFICATION_ACTION_TYPE);
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
}
