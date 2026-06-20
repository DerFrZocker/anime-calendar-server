package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import static de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification.NOTIFICATION_TYPE;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionType;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationCreateData;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationService;
import de.derfrzocker.anime.calendar.server.integration.event.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeName;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.NewAnimeTaskConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationHelperService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class NewAnimeTask {

    private static final NameType NAME_TYPE = new NameType("main");

    @Inject
    NewAnimeNotificationService newAnimeNotificationService;
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
    NewAnimeTaskConfig config;

    public void onNewAnime(@Observes PostNewAnimeFoundEvent foundEvent) {
        RequestContext context = foundEvent.context();
        NotificationId notificationId = createNewAnimeNotification(
                foundEvent.fromIntegration(),
                foundEvent.fromAnimeId(),
                context);

        int priority = 0;

        for (NameSearchResult result : foundEvent
                .potentialNames()
                .stream()
                .sorted(Comparator.comparing(NameSearchResult::score))
                .toList()) {
            createNewAnimeAction(
                    notificationId,
                    priority++,
                    foundEvent.episodeCount(),
                    result,
                    foundEvent.fromIntegration(),
                    foundEvent.fromAnimeId(),
                    context);
        }

        if (Objects.equals(foundEvent.fromIntegration(), IntegrationIds.SYOBOI)) {
            createIgnoreTIDDataAction(notificationId, priority++, new TID(foundEvent.fromAnimeId().raw()), context);
        }

        createManualNewAnimeAction(
                notificationId,
                priority,
                foundEvent.episodeCount(),
                foundEvent.fromIntegration(),
                foundEvent.fromAnimeId(),
                context);

        this.helperService.send(notificationId, context);
    }

    private NotificationId createNewAnimeNotification(
            IntegrationId integrationId,
            IntegrationAnimeId integrationAnimeId,
            RequestContext context) {

        Instant validUntil = Instant.now().plus(this.config.validDuration());
        var createData = new NotificationCreateData(NOTIFICATION_TYPE, validUntil);
        NotificationId id = this.notificationService.createWithData(createData, context).id();

        var specificCreateData = new NewAnimeNotificationCreateData(integrationId, integrationAnimeId);
        this.newAnimeNotificationService.createWithData(id, specificCreateData, context);

        return id;
    }

    private NotificationActionId createAction(
            NotificationId id,
            NotificationActionType actionType,
            int priority,
            boolean requireUserInput,
            RequestContext context) {

        var createData = new NotificationActionCreateData(id, actionType, priority, requireUserInput);
        return this.actionService.createWithData(createData, context).id();
    }

    private void createNewAnimeAction(
            NotificationId id,
            int priority,
            int episodeCount,
            NameSearchResult result,
            IntegrationId fromIntegrationId,
            IntegrationAnimeId fromIntegrationAnimeId,
            RequestContext context) {

        NotificationActionId actionId = createAction(
                id,
                NewAnimeNotificationAction.NOTIFICATION_ACTION_TYPE,
                priority,
                false,
                context);

        var createData = new NewAnimeNotificationActionCreateData(
                findMainName(result).map(AnimeName::name).orElse(null),
                episodeCount,
                result.score(),
                fromIntegrationId,
                fromIntegrationAnimeId,
                result.animeNameHolder().integrationId(),
                result.animeNameHolder().integrationAnimeId());
        this.newAnimeActionService.createWithData(actionId, createData, context);
    }

    private void createIgnoreTIDDataAction(NotificationId id, int priority, TID tid, RequestContext context) {
        NotificationActionId actionId = createAction(
                id,
                IgnoreTIDDataNotificationAction.NOTIFICATION_ACTION_TYPE,
                priority,
                false,
                context);

        var createData = new IgnoreTIDDataNotificationActionCreateData(tid);
        this.ignoreTIDDataActionService.createWithData(actionId, createData, context);
    }

    private void createManualNewAnimeAction(
            NotificationId id,
            int priority,
            int episodeCount,
            IntegrationId fromIntegrationId,
            IntegrationAnimeId fromIntegrationAnimeId,
            RequestContext context) {

        NotificationActionId actionId = createAction(
                id,
                NewAnimeNotificationAction.NOTIFICATION_ACTION_TYPE,
                priority,
                true,
                context);

        var createData = new NewAnimeNotificationActionCreateData(
                null,
                episodeCount,
                0,
                fromIntegrationId,
                fromIntegrationAnimeId,
                null,
                null);
        this.newAnimeActionService.createWithData(actionId, createData, context);
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
            Log.warnf("Could not find main title for search result '%s'.", result);
            return Optional.empty();
        }

        return Optional.of(mainName);
    }
}
