package de.derfrzocker.anime.calendar.server.integration.impl.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationUpdateData;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PostStreamingNotificationCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PostStreamingNotificationDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PostStreamingNotificationUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PreStreamingNotificationCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PreStreamingNotificationDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PreStreamingNotificationUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class StreamingNotificationEventPublisher {

    @Inject
    Event<PreStreamingNotificationCreateEvent> preCreateEvent;
    @Inject
    Event<PostStreamingNotificationCreateEvent> postCreateEvent;

    @Inject
    Event<PreStreamingNotificationUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostStreamingNotificationUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreStreamingNotificationDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostStreamingNotificationDeleteEvent> postDeleteEvent;

    public void firePreCreate(StreamingNotification notification,
                              StreamingNotificationCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreStreamingNotificationCreateEvent(notification, createData, context));
    }

    public void firePostCreate(StreamingNotification notification,
                               StreamingNotificationCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostStreamingNotificationCreateEvent(notification, createData, context));
    }

    public void firePreUpdate(StreamingNotification current,
                              StreamingNotification updated,
                              StreamingNotificationUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreStreamingNotificationUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(StreamingNotification current,
                               StreamingNotification updated,
                               StreamingNotificationUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostStreamingNotificationUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(StreamingNotification notification, RequestContext context) {
        this.preDeleteEvent.fire(new PreStreamingNotificationDeleteEvent(notification, context));
    }

    public void firePostDelete(StreamingNotification notification, RequestContext context) {
        this.postDeleteEvent.fire(new PostStreamingNotificationDeleteEvent(notification, context));
    }
}
