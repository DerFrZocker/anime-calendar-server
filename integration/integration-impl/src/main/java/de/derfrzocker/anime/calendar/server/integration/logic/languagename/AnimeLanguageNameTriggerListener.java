package de.derfrzocker.anime.calendar.server.integration.logic.languagename;

import de.derfrzocker.anime.calendar.server.integration.name.event.PostAnimeNameHolderCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.name.event.PostAnimeNameHolderUpdateEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
class AnimeLanguageNameTriggerListener {

    @Inject
    AnimeLanguageNameJob job;

    void onPostAnimeNameHolderUpdate(@Observes PostAnimeNameHolderUpdateEvent event) {
        this.job.execute(event.updated(), event.context());
    }

    void onPostAnimeNameHolderCreate(@Observes PostAnimeNameHolderCreateEvent event) {
        this.job.execute(event.animeNameHolder(), event.context());
    }
}
