package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.SyoboiConfig;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
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
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NewAnimeTask {

    private static final Logger LOG = Logger.getLogger(NewAnimeTask.class);

    public static final NotificationType NOTIFICATION_TYPE = new NotificationType("NewAnime");
    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("NewAnime");

    private static final NameType NAME_TYPE = new NameType("main");
    private static final NameLanguage NAME_LANGUAGE = new NameLanguage("x-jat");

    @Inject
    NewAnimeNotificationActionService newAnimeActionService;
    @Inject
    NotificationActionService actionService;
    @Inject
    NotificationService notificationService;
    @Inject
    NotificationHelperService helperService;
    @Inject
    SyoboiConfig config;

    public void onNewAnime(@Observes PostNewAnimeFoundEvent foundEvent) {
        RequestContext context = foundEvent.context();
        Notification notification = createNewNotification(context);

        for (NameSearchResult result : foundEvent.potentialNames()) {
            NotificationAction notificationAction = createNewNotificationAction(notification.id(), context);

            createNewNotificationAction(notificationAction.id(),
                                        result,
                                        foundEvent.fromIntegration(),
                                        foundEvent.fromAnimeId(),
                                        context);
        }

        this.helperService.send(notification.id(), context);
    }

    private Notification createNewNotification(RequestContext context) {
        Instant validUntil = Instant.now().plus(this.config.getNewAnimeActionValidLength());
        NotificationCreateData createData = new NotificationCreateData(NOTIFICATION_TYPE, validUntil);

        return this.notificationService.createWithData(createData, context);
    }

    private NotificationAction createNewNotificationAction(NotificationId id, RequestContext context) {
        NotificationActionCreateData createData = new NotificationActionCreateData(id, NOTIFICATION_ACTION_TYPE);
        return this.actionService.createWithData(createData, context);
    }

    private void createNewNotificationAction(NotificationActionId actionId,
                                             NameSearchResult result,
                                             IntegrationId fromIntegrationId,
                                             IntegrationAnimeId fromIntegrationAnimeId,
                                             RequestContext context) {
        NewAnimeNotificationActionCreateData createData = createData(result, fromIntegrationId, fromIntegrationAnimeId);
        this.newAnimeActionService.createWithData(actionId, createData, context);
    }

    private NewAnimeNotificationActionCreateData createData(NameSearchResult result,
                                                            IntegrationId fromIntegrationId,
                                                            IntegrationAnimeId fromIntegrationAnimeId) {
        Map<IntegrationId, IntegrationAnimeId> links = new HashMap<>();
        links.put(fromIntegrationId, fromIntegrationAnimeId);
        links.put(result.animeNameHolder().integrationId(), result.animeNameHolder().integrationAnimeId());

        // TODO 2025-04-05: Get episode from somewhere
        return new NewAnimeNotificationActionCreateData(findMainName(result).map(AnimeName::name).orElse(null),
                                                        12,
                                                        result.score(),
                                                        links);
    }

    private Optional<AnimeName> findMainName(NameSearchResult result) {
        AnimeName mainName = null;
        for (AnimeName animeName : result.animeNameHolder().names()) {
            if (!NAME_LANGUAGE.equals(animeName.language())) {
                continue;
            }

            if (!NAME_TYPE.equals(animeName.type())) {
                continue;
            }

            mainName = animeName;
            break;
        }

        if (mainName == null) {
            LOG.warnv("Could not find main title for search result '{}'.", result);
            return Optional.empty();
        }

        return Optional.of(mainName);
    }
}
