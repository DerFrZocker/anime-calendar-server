package de.derfrzocker.anime.calendar.server.model.domain.event.integration;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;

public record PostAnimeIntegrationLinkCreateEvent(AnimeId animeId, IntegrationId integrationId,
                                                  IntegrationAnimeId integrationAnimeId, AnimeIntegrationLink link,
                                                  RequestContext context) {

}
