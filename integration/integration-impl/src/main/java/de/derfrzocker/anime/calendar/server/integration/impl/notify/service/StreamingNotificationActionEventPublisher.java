package de.derfrzocker.anime.calendar.server.integration.impl.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PostStreamingNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PostStreamingNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PostStreamingNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PreStreamingNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PreStreamingNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.notify.event.PreStreamingNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class StreamingNotificationActionEventPublisher {

    @Inject
    Event<PreStreamingNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostStreamingNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreStreamingNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostStreamingNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreStreamingNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostStreamingNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(StreamingNotificationAction action,
                              StreamingNotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreStreamingNotificationActionCreateEvent(action, createData, context));
    }

    public void firePostCreate(StreamingNotificationAction action,
                               StreamingNotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostStreamingNotificationActionCreateEvent(action, createData, context));
    }

    public void firePreUpdate(StreamingNotificationAction current,
                              StreamingNotificationAction updated,
                              StreamingNotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreStreamingNotificationActionUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(StreamingNotificationAction current,
                               StreamingNotificationAction updated,
                               StreamingNotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostStreamingNotificationActionUpdateEvent(current,
                                                                                 updated,
                                                                                 updateData,
                                                                                 context));
    }

    public void firePreDelete(StreamingNotificationAction action, RequestContext context) {
        this.preDeleteEvent.fire(new PreStreamingNotificationActionDeleteEvent(action, context));
    }

    public void firePostDelete(StreamingNotificationAction action, RequestContext context) {
        this.postDeleteEvent.fire(new PostStreamingNotificationActionDeleteEvent(action, context));
    }
}
