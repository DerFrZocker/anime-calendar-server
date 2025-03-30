package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTrackingChannelNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTrackingChannelNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTrackingChannelNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreTrackingChannelNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreTrackingChannelNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreTrackingChannelNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class TrackingChannelNotificationActionEventPublisher {

    @Inject
    Event<PreTrackingChannelNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostTrackingChannelNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreTrackingChannelNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostTrackingChannelNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreTrackingChannelNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostTrackingChannelNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(TrackingChannelNotificationAction action,
                              TrackingChannelNotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreTrackingChannelNotificationActionCreateEvent(action, createData, context));
    }

    public void firePostCreate(TrackingChannelNotificationAction action,
                               TrackingChannelNotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostTrackingChannelNotificationActionCreateEvent(action, createData, context));
    }

    public void firePreUpdate(TrackingChannelNotificationAction current,
                              TrackingChannelNotificationAction updated,
                              TrackingChannelNotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreTrackingChannelNotificationActionUpdateEvent(current,
                                                                                     updated,
                                                                                     updateData,
                                                                                     context));
    }

    public void firePostUpdate(TrackingChannelNotificationAction current,
                               TrackingChannelNotificationAction updated,
                               TrackingChannelNotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostTrackingChannelNotificationActionUpdateEvent(current,
                                                                                       updated,
                                                                                       updateData,
                                                                                       context));
    }

    public void firePreDelete(TrackingChannelNotificationAction action, RequestContext context) {
        this.preDeleteEvent.fire(new PreTrackingChannelNotificationActionDeleteEvent(action, context));
    }

    public void firePostDelete(TrackingChannelNotificationAction action, RequestContext context) {
        this.postDeleteEvent.fire(new PostTrackingChannelNotificationActionDeleteEvent(action, context));
    }
}
