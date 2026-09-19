package de.derfrzocker.anime.calendar.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.integration.event.PostAnimeIntegrationLinkCreateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class AnimeIntegrationLinkEventPublisher {

    @Inject
    Event<PostAnimeIntegrationLinkCreateEvent> postCreateEvent;

    public void firePostCreate(AnimeIntegrationLink animeIntegrationLink,
                               AnimeIntegrationLinkCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeIntegrationLinkCreateEvent(animeIntegrationLink, createData, context));
    }
}
