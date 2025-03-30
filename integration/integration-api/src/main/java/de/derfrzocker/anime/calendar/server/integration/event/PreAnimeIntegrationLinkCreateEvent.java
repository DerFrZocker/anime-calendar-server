package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;

public record PreAnimeIntegrationLinkCreateEvent(AnimeIntegrationLink animeIntegrationLink,
                                                 AnimeIntegrationLinkCreateData createData, RequestContext context) {

}
