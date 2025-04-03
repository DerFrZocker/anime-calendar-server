package de.derfrzocker.anime.calendar.server.core.listener.validation;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueException;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.event.PreAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkCreateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@Dependent
public class AnimePresentListener {

    @Inject
    AnimeService service;

    public void onPreCalendarAnimeLinkCreate(@Observes PreCalendarAnimeLinkCreateEvent event) {
        ensurePresent(event.animeId(), event.context());
    }

    public void onPreAnimeIntegrationLinkCreate(@Observes PreAnimeIntegrationLinkCreateEvent event) {
        ensurePresent(event.animeIntegrationLink().animeId(), event.context());
    }

    private void ensurePresent(AnimeId id, RequestContext context) {
        if (this.service.getById(id, context).isEmpty()) {
            throw InvalidValueException.with("animeId", id);
        }
    }
}
