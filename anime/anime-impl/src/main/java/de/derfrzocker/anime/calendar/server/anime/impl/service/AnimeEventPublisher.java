package de.derfrzocker.anime.calendar.server.anime.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.event.PostAnimeCreateEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PostAnimeDeleteEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PostAnimeUpdateEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PreAnimeCreateEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PreAnimeDeleteEvent;
import de.derfrzocker.anime.calendar.server.anime.event.PreAnimeUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class AnimeEventPublisher {

    @Inject
    Event<PreAnimeCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeCreateEvent> postCreateEvent;

    @Inject
    Event<PreAnimeUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostAnimeUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreAnimeDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostAnimeDeleteEvent> postDeleteEvent;

    public void firePreCreate(Anime anime, AnimeCreateData createData, RequestContext context) {
        this.preCreateEvent.fire(new PreAnimeCreateEvent(anime, createData, context));
    }

    public void firePostCreate(Anime anime, AnimeCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeCreateEvent(anime, createData, context));
    }

    public void firePreUpdate(Anime current,
                              Anime updated,
                              AnimeUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreAnimeUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(Anime current,
                               Anime updated,
                               AnimeUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostAnimeUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(Anime anime, RequestContext context) {
        this.preDeleteEvent.fire(new PreAnimeDeleteEvent(anime, context));
    }

    public void firePostDelete(Anime anime, RequestContext context) {
        this.postDeleteEvent.fire(new PostAnimeDeleteEvent(anime, context));
    }
}
