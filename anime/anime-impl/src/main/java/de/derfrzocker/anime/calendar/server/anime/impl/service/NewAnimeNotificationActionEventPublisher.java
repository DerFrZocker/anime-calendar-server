package de.derfrzocker.anime.calendar.server.anime.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.anime.event.PostNewAnimeNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PostNewAnimeNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PostNewAnimeNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PreNewAnimeNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PreNewAnimeNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PreNewAnimeNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class NewAnimeNotificationActionEventPublisher {

    @Inject
    Event<PreNewAnimeNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostNewAnimeNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreNewAnimeNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostNewAnimeNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreNewAnimeNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostNewAnimeNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(NewAnimeNotificationAction action,
                              NewAnimeNotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreNewAnimeNotificationActionCreateEvent(action, createData, context));
    }

    public void firePostCreate(NewAnimeNotificationAction action,
                               NewAnimeNotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostNewAnimeNotificationActionCreateEvent(action, createData, context));
    }

    public void firePreUpdate(NewAnimeNotificationAction current,
                              NewAnimeNotificationAction updated,
                              NewAnimeNotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreNewAnimeNotificationActionUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(NewAnimeNotificationAction current,
                               NewAnimeNotificationAction updated,
                               NewAnimeNotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostNewAnimeNotificationActionUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(NewAnimeNotificationAction action, RequestContext context) {
        this.preDeleteEvent.fire(new PreNewAnimeNotificationActionDeleteEvent(action, context));
    }

    public void firePostDelete(NewAnimeNotificationAction action, RequestContext context) {
        this.postDeleteEvent.fire(new PostNewAnimeNotificationActionDeleteEvent(action, context));
    }
}
