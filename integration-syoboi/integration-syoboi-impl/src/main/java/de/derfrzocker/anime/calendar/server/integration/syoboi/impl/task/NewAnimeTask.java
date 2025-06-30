package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.event.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeName;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.SyoboiConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
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
import java.util.Objects;
import java.util.Optional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NewAnimeTask {

    private static final Logger LOG = Logger.getLogger(NewAnimeTask.class);

    public static final NotificationType NOTIFICATION_TYPE = new NotificationType("NewAnime");
    public static final NotificationActionType NEW_ANIME_ACTION_TYPE = new NotificationActionType("NewAnime");
    public static final NotificationActionType IGNORE_ACTION_TYPE = new NotificationActionType("IgnoreTIDData");

    private static final NameType NAME_TYPE = new NameType("main");

    @Inject
    NewAnimeNotificationActionService newAnimeActionService;
    @Inject
    IgnoreTIDDataNotificationActionService ignoreTIDDataActionService;
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
            NotificationAction action = createNewAction(notification.id(), context, NEW_ANIME_ACTION_TYPE, false);

            createNewAction(action.id(),
                            result,
                            foundEvent.fromIntegration(),
                            foundEvent.fromAnimeId(),
                            foundEvent.episodeCount(),
                            context);
        }

        if (Objects.equals(foundEvent.fromIntegration(), IntegrationIds.SYOBOI)) {
            NotificationAction action = createNewAction(notification.id(), context, IGNORE_ACTION_TYPE, false);
            createNewAction(action.id(), new TID(foundEvent.fromAnimeId().raw()), context);
        }

        NotificationAction manualAction = createNewAction(notification.id(), context, NEW_ANIME_ACTION_TYPE, true);
        createNewManualAction(manualAction,
                              foundEvent.fromIntegration(),
                              foundEvent.fromAnimeId(),
                              foundEvent.episodeCount(),
                              context);

        this.helperService.send(notification.id(), context);
    }

    private Notification createNewNotification(RequestContext context) {
        Instant validUntil = Instant.now().plus(this.config.getNewAnimeActionValidLength());
        NotificationCreateData createData = new NotificationCreateData(NOTIFICATION_TYPE, validUntil);

        return this.notificationService.createWithData(createData, context);
    }

    private NotificationAction createNewAction(NotificationId id,
                                               RequestContext context,
                                               NotificationActionType actionType,
                                               boolean requireUserInput) {
        NotificationActionCreateData createData = new NotificationActionCreateData(id, actionType, requireUserInput);
        return this.actionService.createWithData(createData, context);
    }

    private void createNewAction(NotificationActionId actionId,
                                 NameSearchResult result,
                                 IntegrationId fromIntegrationId,
                                 IntegrationAnimeId fromIntegrationAnimeId,
                                 int episodeCount,
                                 RequestContext context) {
        NewAnimeNotificationActionCreateData createData = createData(result,
                                                                     fromIntegrationId,
                                                                     fromIntegrationAnimeId,
                                                                     episodeCount);
        this.newAnimeActionService.createWithData(actionId, createData, context);
    }

    private NewAnimeNotificationActionCreateData createData(NameSearchResult result,
                                                            IntegrationId fromIntegrationId,
                                                            IntegrationAnimeId fromIntegrationAnimeId,
                                                            int episodeCount) {
        Map<IntegrationId, IntegrationAnimeId> links = new HashMap<>();
        links.put(fromIntegrationId, fromIntegrationAnimeId);
        links.put(result.animeNameHolder().integrationId(), result.animeNameHolder().integrationAnimeId());

        return new NewAnimeNotificationActionCreateData(findMainName(result).map(AnimeName::name).orElse(null),
                                                        episodeCount,
                                                        result.score(),
                                                        links);
    }

    private void createNewAction(NotificationActionId actionId, TID tid, RequestContext context) {
        IgnoreTIDDataNotificationActionCreateData createData = new IgnoreTIDDataNotificationActionCreateData(tid);
        this.ignoreTIDDataActionService.createWithData(actionId, createData, context);
    }

    private void createNewManualAction(NotificationAction action,
                                       IntegrationId integrationId,
                                       IntegrationAnimeId integrationAnimeId,
                                       int episodeCount,
                                       RequestContext context) {
        NewAnimeNotificationActionCreateData manualActionCreateData = manualCreateData(integrationId,
                                                                                       integrationAnimeId,
                                                                                       episodeCount);
        this.newAnimeActionService.createWithData(action.id(), manualActionCreateData, context);
    }

    private NewAnimeNotificationActionCreateData manualCreateData(IntegrationId integrationId,
                                                                  IntegrationAnimeId integrationAnimeId,
                                                                  int episodeCount) {
        return new NewAnimeNotificationActionCreateData(null,
                                                        episodeCount,
                                                        0,
                                                        Map.of(integrationId, integrationAnimeId));
    }

    private Optional<AnimeName> findMainName(NameSearchResult result) {
        AnimeName mainName = null;
        for (AnimeName animeName : result.animeNameHolder().names()) {
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
