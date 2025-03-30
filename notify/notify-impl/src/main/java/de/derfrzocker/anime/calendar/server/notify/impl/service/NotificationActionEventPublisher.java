package de.derfrzocker.anime.calendar.server.notify.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.notify.event.PostNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PostNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PostNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PreNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PreNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PreNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class NotificationActionEventPublisher {

    @Inject
    Event<PreNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(NotificationAction notification,
                              NotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreNotificationActionCreateEvent(notification, createData, context));
    }

    public void firePostCreate(NotificationAction notification,
                               NotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostNotificationActionCreateEvent(notification, createData, context));
    }

    public void firePreUpdate(NotificationAction current,
                              NotificationAction updated,
                              NotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreNotificationActionUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(NotificationAction current,
                               NotificationAction updated,
                               NotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostNotificationActionUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(NotificationAction notification, RequestContext context) {
        this.preDeleteEvent.fire(new PreNotificationActionDeleteEvent(notification, context));
    }

    public void firePostDelete(NotificationAction notification, RequestContext context) {
        this.postDeleteEvent.fire(new PostNotificationActionDeleteEvent(notification, context));
    }
}
