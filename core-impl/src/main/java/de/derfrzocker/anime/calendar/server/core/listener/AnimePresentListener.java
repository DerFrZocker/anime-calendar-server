package de.derfrzocker.anime.calendar.server.core.listener;

import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkCreateEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@RequestScoped
public class AnimePresentListener {

    @Inject
    AnimeService service;

    public void onPreCalendarAnimeLinkEvent(@Observes PreCalendarAnimeLinkCreateEvent event) {
        ensureIsPresent(event.animeId());
    }

    private void ensureIsPresent(AnimeId id) {
        // TODO 2024-12-09: Null Context
        if (this.service.getById(id, null).isEmpty()) {
            // TODO 2024-12-08: Better exception
            throw new RuntimeException();
        }
    }
}
