package de.derfrzocker.anime.calendar.server.season.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoUpdateData;
import de.derfrzocker.anime.calendar.server.season.event.PostAnimeSeasonInfoCreateEvent;
import de.derfrzocker.anime.calendar.server.season.event.PostAnimeSeasonInfoDeleteEvent;
import de.derfrzocker.anime.calendar.server.season.event.PostAnimeSeasonInfoUpdateEvent;
import de.derfrzocker.anime.calendar.server.season.event.PreAnimeSeasonInfoCreateEvent;
import de.derfrzocker.anime.calendar.server.season.event.PreAnimeSeasonInfoDeleteEvent;
import de.derfrzocker.anime.calendar.server.season.event.PreAnimeSeasonInfoUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class AnimeSeasonInfoEventPublisher {

    @Inject
    Event<PreAnimeSeasonInfoCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeSeasonInfoCreateEvent> postCreateEvent;

    @Inject
    Event<PreAnimeSeasonInfoUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostAnimeSeasonInfoUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreAnimeSeasonInfoDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostAnimeSeasonInfoDeleteEvent> postDeleteEvent;

    public void firePreCreate(AnimeSeasonInfo info, AnimeSeasonInfoCreateData createData, RequestContext context) {
        this.preCreateEvent.fire(new PreAnimeSeasonInfoCreateEvent(info, createData, context));
    }

    public void firePostCreate(AnimeSeasonInfo info, AnimeSeasonInfoCreateData createData, RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeSeasonInfoCreateEvent(info, createData, context));
    }

    public void firePreUpdate(AnimeSeasonInfo current,
                              AnimeSeasonInfo updated,
                              AnimeSeasonInfoUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreAnimeSeasonInfoUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(AnimeSeasonInfo current,
                               AnimeSeasonInfo updated,
                               AnimeSeasonInfoUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostAnimeSeasonInfoUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(AnimeSeasonInfo info, RequestContext context) {
        this.preDeleteEvent.fire(new PreAnimeSeasonInfoDeleteEvent(info, context));
    }

    public void firePostDelete(AnimeSeasonInfo info, RequestContext context) {
        this.postDeleteEvent.fire(new PostAnimeSeasonInfoDeleteEvent(info, context));
    }
}
