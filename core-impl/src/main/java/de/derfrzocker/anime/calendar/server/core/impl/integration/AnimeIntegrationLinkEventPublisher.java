package de.derfrzocker.anime.calendar.server.core.impl.integration;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.event.integration.PostAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.integration.PreAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@RequestScoped
public class AnimeIntegrationLinkEventPublisher {

    @Inject
    Event<PreAnimeIntegrationLinkCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeIntegrationLinkCreateEvent> postCreateEvent;

    public void firePreCreateEvent(AnimeId animeId,
                                   IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   AnimeIntegrationLink link,
                                   RequestContext context) {

        this.preCreateEvent.fire(new PreAnimeIntegrationLinkCreateEvent(animeId,
                                                                        integrationId,
                                                                        integrationAnimeId,
                                                                        link,
                                                                        context));
    }

    public void firePostCreateEvent(AnimeId animeId,
                                    IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    AnimeIntegrationLink link,
                                    RequestContext context) {

        this.postCreateEvent.fire(new PostAnimeIntegrationLinkCreateEvent(animeId,
                                                                          integrationId,
                                                                          integrationAnimeId,
                                                                          link,
                                                                          context));
    }
}
