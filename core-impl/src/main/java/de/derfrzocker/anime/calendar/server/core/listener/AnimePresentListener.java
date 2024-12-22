package de.derfrzocker.anime.calendar.server.core.listener;

import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkCreateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@Dependent
public class AnimePresentListener {

    @Inject
    AnimeService service;

    public void onPreCalendarAnimeLinkEvent(@Observes PreCalendarAnimeLinkCreateEvent event) {
        ensureIsPresent(event.animeId(), event.context());
    }

    private void ensureIsPresent(AnimeId id, RequestContext context) {
        if (this.service.getById(id, context).isEmpty()) {
            // TODO 2024-12-08: Better exception
            throw new RuntimeException();
        }
    }
}
