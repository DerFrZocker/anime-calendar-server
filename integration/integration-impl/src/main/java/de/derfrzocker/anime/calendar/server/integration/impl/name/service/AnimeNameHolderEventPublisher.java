package de.derfrzocker.anime.calendar.server.integration.impl.name.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderUpdateData;
import de.derfrzocker.anime.calendar.server.integration.name.event.PostAnimeNameHolderCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.name.event.PostAnimeNameHolderDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.name.event.PostAnimeNameHolderUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.name.event.PreAnimeNameHolderCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.name.event.PreAnimeNameHolderDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.name.event.PreAnimeNameHolderUpdateEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
class AnimeNameHolderEventPublisher {

    @Inject
    Event<PreAnimeNameHolderCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeNameHolderCreateEvent> postCreateEvent;

    @Inject
    Event<PreAnimeNameHolderUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostAnimeNameHolderUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreAnimeNameHolderDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostAnimeNameHolderDeleteEvent> postDeleteEvent;

    public void firePreCreate(AnimeNameHolder animeNameHolder,
                              AnimeNameHolderCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreAnimeNameHolderCreateEvent(animeNameHolder, createData, context));
    }

    public void firePostCreate(AnimeNameHolder animeNameHolder,
                               AnimeNameHolderCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeNameHolderCreateEvent(animeNameHolder, createData, context));
    }

    public void firePreUpdate(AnimeNameHolder current,
                              AnimeNameHolder updated,
                              AnimeNameHolderUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreAnimeNameHolderUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(AnimeNameHolder current,
                               AnimeNameHolder updated,
                               AnimeNameHolderUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostAnimeNameHolderUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(AnimeNameHolder animeNameHolder, RequestContext context) {
        this.preDeleteEvent.fire(new PreAnimeNameHolderDeleteEvent(animeNameHolder, context));
    }

    public void firePostDelete(AnimeNameHolder animeNameHolder, RequestContext context) {
        this.postDeleteEvent.fire(new PostAnimeNameHolderDeleteEvent(animeNameHolder, context));
    }
}
