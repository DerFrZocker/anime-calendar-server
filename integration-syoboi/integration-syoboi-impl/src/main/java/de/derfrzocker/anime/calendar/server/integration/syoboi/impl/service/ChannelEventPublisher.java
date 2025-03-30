package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostChannelCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostChannelDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostChannelUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreChannelCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreChannelDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PreChannelUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class ChannelEventPublisher {

    @Inject
    Event<PreChannelCreateEvent> preCreateEvent;
    @Inject
    Event<PostChannelCreateEvent> postCreateEvent;

    @Inject
    Event<PreChannelUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostChannelUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreChannelDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostChannelDeleteEvent> postDeleteEvent;

    public void firePreCreate(Channel channel, ChannelCreateData createData, RequestContext context) {
        this.preCreateEvent.fire(new PreChannelCreateEvent(channel, createData, context));
    }

    public void firePostCreate(Channel channel, ChannelCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostChannelCreateEvent(channel, createData, context));
    }

    public void firePreUpdate(Channel current, Channel updated, ChannelUpdateData updateData, RequestContext context) {
        this.preUpdateEvent.fire(new PreChannelUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(Channel current, Channel updated, ChannelUpdateData updateData, RequestContext context) {
        this.postUpdateEvent.fire(new PostChannelUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(Channel channel, RequestContext context) {
        this.preDeleteEvent.fire(new PreChannelDeleteEvent(channel, context));
    }

    public void firePostDelete(Channel channel, RequestContext context) {
        this.postDeleteEvent.fire(new PostChannelDeleteEvent(channel, context));
    }
}
