package de.derfrzocker.anime.calendar.anime.impl.service;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.anime.api.AnimeCreateData;
import de.derfrzocker.anime.calendar.anime.event.PostAnimeCreateEvent;
import de.derfrzocker.anime.calendar.core.RequestContext;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class AnimeEventPublisher {

    @Inject
    Event<PostAnimeCreateEvent> postCreateEvent;

    public void firePostCreate(Anime anime, AnimeCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeCreateEvent(anime, createData, context));
    }
}
