package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreTIDDataCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreTIDDataDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreTIDDataUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class TIDDataEventPublisher {

    @Inject
    Event<PreTIDDataCreateEvent> preCreateEvent;
    @Inject
    Event<PostTIDDataCreateEvent> postCreateEvent;

    @Inject
    Event<PreTIDDataUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostTIDDataUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreTIDDataDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostTIDDataDeleteEvent> postDeleteEvent;

    public void firePreCreate(TIDData tidData, TIDDataCreateData createData, RequestContext context) {
        this.preCreateEvent.fire(new PreTIDDataCreateEvent(tidData, createData, context));
    }

    public void firePostCreate(TIDData tidData, TIDDataCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostTIDDataCreateEvent(tidData, createData, context));
    }

    public void firePreUpdate(TIDData current, TIDData updated, TIDDataUpdateData updateData, RequestContext context) {
        this.preUpdateEvent.fire(new PreTIDDataUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(TIDData current, TIDData updated, TIDDataUpdateData updateData, RequestContext context) {
        this.postUpdateEvent.fire(new PostTIDDataUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(TIDData tidData, RequestContext context) {
        this.preDeleteEvent.fire(new PreTIDDataDeleteEvent(tidData, context));
    }

    public void firePostDelete(TIDData tidData, RequestContext context) {
        this.postDeleteEvent.fire(new PostTIDDataDeleteEvent(tidData, context));
    }
}
