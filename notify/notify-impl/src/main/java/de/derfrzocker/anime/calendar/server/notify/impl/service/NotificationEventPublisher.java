package de.derfrzocker.anime.calendar.server.notify.impl.service;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationUpdateData;
import de.derfrzocker.anime.calendar.server.notify.event.PostNotificationCreateEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PostNotificationDeleteEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PostNotificationUpdateEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PreNotificationCreateEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PreNotificationDeleteEvent;
import de.derfrzocker.anime.calendar.server.notify.event.PreNotificationUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class NotificationEventPublisher {

    @Inject
    Event<PreNotificationCreateEvent> preCreateEvent;
    @Inject
    Event<PostNotificationCreateEvent> postCreateEvent;

    @Inject
    Event<PreNotificationUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostNotificationUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreNotificationDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostNotificationDeleteEvent> postDeleteEvent;

    public void firePreCreate(Notification notification, NotificationCreateData createData, RequestContext context) {
        this.preCreateEvent.fire(new PreNotificationCreateEvent(notification, createData, context));
    }

    public void firePostCreate(Notification notification, NotificationCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostNotificationCreateEvent(notification, createData, context));
    }

    public void firePreUpdate(Notification current,
                              Notification updated,
                              NotificationUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreNotificationUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(Notification current,
                               Notification updated,
                               NotificationUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostNotificationUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(Notification notification, RequestContext context) {
        this.preDeleteEvent.fire(new PreNotificationDeleteEvent(notification, context));
    }

    public void firePostDelete(Notification notification, RequestContext context) {
        this.postDeleteEvent.fire(new PostNotificationDeleteEvent(notification, context));
    }
}
