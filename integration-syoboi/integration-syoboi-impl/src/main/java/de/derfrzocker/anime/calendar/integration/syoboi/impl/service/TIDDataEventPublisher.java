package de.derfrzocker.anime.calendar.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.integration.syoboi.event.PostTIDDataCreateEvent;
import de.derfrzocker.anime.calendar.integration.syoboi.event.PostTIDDataUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class TIDDataEventPublisher {

    @Inject
    Event<PostTIDDataCreateEvent> postCreateEvent;

    @Inject
    Event<PostTIDDataUpdateEvent> postUpdateEvent;

    public void firePostCreate(TIDData tidData, TIDDataCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostTIDDataCreateEvent(tidData, createData, context));
    }

    public void firePostUpdate(TIDData current, TIDData updated, TIDDataUpdateData updateData, RequestContext context) {
        this.postUpdateEvent.fire(new PostTIDDataUpdateEvent(current, updated, updateData, context));
    }
}
