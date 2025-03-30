package de.derfrzocker.anime.calendar.server.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkUpdateData;
import de.derfrzocker.anime.calendar.server.integration.event.PostAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PostAnimeIntegrationLinkDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PostAnimeIntegrationLinkUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreAnimeIntegrationLinkDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreAnimeIntegrationLinkUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class AnimeIntegrationLinkEventPublisher {

    @Inject
    Event<PreAnimeIntegrationLinkCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeIntegrationLinkCreateEvent> postCreateEvent;

    @Inject
    Event<PreAnimeIntegrationLinkUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostAnimeIntegrationLinkUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreAnimeIntegrationLinkDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostAnimeIntegrationLinkDeleteEvent> postDeleteEvent;

    public void firePreCreate(AnimeIntegrationLink animeIntegrationLink,
                              AnimeIntegrationLinkCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreAnimeIntegrationLinkCreateEvent(animeIntegrationLink, createData, context));
    }

    public void firePostCreate(AnimeIntegrationLink animeIntegrationLink,
                               AnimeIntegrationLinkCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostAnimeIntegrationLinkCreateEvent(animeIntegrationLink, createData, context));
    }

    public void firePreUpdate(AnimeIntegrationLink current,
                              AnimeIntegrationLink updated,
                              AnimeIntegrationLinkUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreAnimeIntegrationLinkUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(AnimeIntegrationLink current,
                               AnimeIntegrationLink updated,
                               AnimeIntegrationLinkUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostAnimeIntegrationLinkUpdateEvent(current, updated, updateData, context));
    }

    public void firePreDelete(AnimeIntegrationLink animeIntegrationLink, RequestContext context) {
        this.preDeleteEvent.fire(new PreAnimeIntegrationLinkDeleteEvent(animeIntegrationLink, context));
    }

    public void firePostDelete(AnimeIntegrationLink animeIntegrationLink, RequestContext context) {
        this.postDeleteEvent.fire(new PostAnimeIntegrationLinkDeleteEvent(animeIntegrationLink, context));
    }
}
