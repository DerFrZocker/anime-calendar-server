package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostIgnoreTIDDataNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostIgnoreTIDDataNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostIgnoreTIDDataNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreIgnoreTIDDataNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreIgnoreTIDDataNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreIgnoreTIDDataNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class IgnoreTIDDataNotificationActionEventPublisher {

    @Inject
    Event<PreIgnoreTIDDataNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostIgnoreTIDDataNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreIgnoreTIDDataNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostIgnoreTIDDataNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreIgnoreTIDDataNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostIgnoreTIDDataNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(IgnoreTIDDataNotificationAction action,
                              IgnoreTIDDataNotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreIgnoreTIDDataNotificationActionCreateEvent(action, createData, context));
    }

    public void firePostCreate(IgnoreTIDDataNotificationAction action,
                               IgnoreTIDDataNotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostIgnoreTIDDataNotificationActionCreateEvent(action, createData, context));
    }

    public void firePreUpdate(IgnoreTIDDataNotificationAction current,
                              IgnoreTIDDataNotificationAction updated,
                              IgnoreTIDDataNotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreIgnoreTIDDataNotificationActionUpdateEvent(current,
                                                                                   updated,
                                                                                   updateData,
                                                                                   context));
    }

    public void firePostUpdate(IgnoreTIDDataNotificationAction current,
                               IgnoreTIDDataNotificationAction updated,
                               IgnoreTIDDataNotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostIgnoreTIDDataNotificationActionUpdateEvent(current,
                                                                                     updated,
                                                                                     updateData,
                                                                                     context));
    }

    public void firePreDelete(IgnoreTIDDataNotificationAction action, RequestContext context) {
        this.preDeleteEvent.fire(new PreIgnoreTIDDataNotificationActionDeleteEvent(action, context));
    }

    public void firePostDelete(IgnoreTIDDataNotificationAction action, RequestContext context) {
        this.postDeleteEvent.fire(new PostIgnoreTIDDataNotificationActionDeleteEvent(action, context));
    }
}
