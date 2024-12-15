package de.derfrzocker.anime.calendar.server.core.impl.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostAnimeCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostAnimeDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostAnimeUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PreAnimeCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PreAnimeDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PreAnimeUpdateEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@RequestScoped
public class AnimeEventPublisher {

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

    public void firePreCreateEvent(AnimeCreateData createData, Anime anime, RequestContext context) {
        this.preCreateEvent.fire(new PreAnimeCreateEvent(createData, anime, context));
    }

    public void firePostCreateEvent(AnimeCreateData createData, Anime anime, RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeCreateEvent(createData, anime, context));
    }

    public void firePreUpdateEvent(AnimeId id,
                                   AnimeUpdateData updateData,
                                   Anime current,
                                   Anime updated,
                                   RequestContext context) {

        this.preUpdateEvent.fire(new PreAnimeUpdateEvent(id, updateData, current, updated, context));
    }

    public void firePostUpdateEvent(AnimeId id,
                                    AnimeUpdateData updateData,
                                    Anime current,
                                    Anime updated,
                                    RequestContext context) {

        this.postUpdateEvent.fire(new PostAnimeUpdateEvent(id, updateData, current, updated, context));
    }

    public void firePreDeleteEvent(Anime anime, RequestContext context) {
        this.preDeleteEvent.fire(new PreAnimeDeleteEvent(anime, context));
    }

    public void firePostDeleteEvent(Anime anime, RequestContext context) {
        this.postDeleteEvent.fire(new PostAnimeDeleteEvent(anime, context));
    }
}
