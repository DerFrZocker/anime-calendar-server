package de.derfrzocker.anime.calendar.notify.impl.service;

import static de.derfrzocker.anime.calendar.notify.exception.NotificationActionExceptions.notFound;
import static de.derfrzocker.anime.calendar.notify.exception.NotificationExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.notify.exception.NotificationExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.notify.api.Notification;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.notify.event.NotificationActionTriggerEvent;
import de.derfrzocker.anime.calendar.notify.event.NotificationSendEvent;
import de.derfrzocker.anime.calendar.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.notify.service.NotificationHelperService;
import de.derfrzocker.anime.calendar.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.stream.Stream;

@ApplicationScoped
public class NotificationHelperServiceImpl implements NotificationHelperService {

    @Inject
    NotificationService notificationService;
    @Inject
    NotificationActionService notificationActionService;
    @Inject
    Event<NotificationSendEvent> notificationSendEvent;
    @Inject
    Event<NotificationActionTriggerEvent> notificationActionTriggerEvent;

    @Override
    public void send(NotificationId id, RequestContext context) {
        Notification notification = this.notificationService.getById(id, context).orElseThrow(notFound(id));

        try (Stream<NotificationAction> actionStream = this.notificationActionService.getAllWithData(id, context)) {
            this.notificationSendEvent.fire(new NotificationSendEvent(
                    notification,
                    actionStream.sorted(Comparator.comparing(NotificationAction::priority)).toList(),
                    context));
        }
    }

    @Override
    public void execute(NotificationActionId id, RequestContext context) {
        NotificationAction notificationAction = this.notificationActionService
                .getById(id, context)
                .orElseThrow(notFound(id));
        NotificationId notificationId = notificationAction.notificationId();
        Notification notification = this.notificationService
                .getById(notificationId, context)
                .orElseThrow(inconsistentNotFound(notificationId));

        this.notificationActionTriggerEvent.fire(new NotificationActionTriggerEvent(
                notification,
                notificationAction,
                context));
    }
}
