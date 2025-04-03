package de.derfrzocker.anime.calendar.server.impl.namelink.listener;

import de.derfrzocker.anime.calendar.server.anime.event.PostAnimeCreateEvent;
import de.derfrzocker.anime.calendar.server.impl.namelink.handler.NameLinkRequestHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class NameLinkAnimeCreateListener {

    @Inject
    NameLinkRequestHandler requestHandler;

    public void onAnimeCreate(@Observes PostAnimeCreateEvent event) {
        this.requestHandler.checkForLinks(event.anime(), event.context()).subscribe().asCompletionStage();
    }
}
