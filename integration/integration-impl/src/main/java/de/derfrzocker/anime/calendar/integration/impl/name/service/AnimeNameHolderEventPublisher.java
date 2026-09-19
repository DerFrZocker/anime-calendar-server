package de.derfrzocker.anime.calendar.integration.impl.name.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolderUpdateData;
import de.derfrzocker.anime.calendar.integration.name.event.PostAnimeNameHolderCreateEvent;
import de.derfrzocker.anime.calendar.integration.name.event.PostAnimeNameHolderUpdateEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
class AnimeNameHolderEventPublisher {

    @Inject
    Event<PostAnimeNameHolderCreateEvent> postCreateEvent;

    @Inject
    Event<PostAnimeNameHolderUpdateEvent> postUpdateEvent;

    public void firePostCreate(AnimeNameHolder animeNameHolder,
                               AnimeNameHolderCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeNameHolderCreateEvent(animeNameHolder, createData, context));
    }

    public void firePostUpdate(AnimeNameHolder current,
                               AnimeNameHolder updated,
                               AnimeNameHolderUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostAnimeNameHolderUpdateEvent(current, updated, updateData, context));
    }
}
