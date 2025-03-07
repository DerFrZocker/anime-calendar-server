package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PreAnimeIntegrationLinkCreateEvent(AnimeIntegrationLink animeIntegrationLink,
                                                 AnimeIntegrationLinkCreateData createData, RequestContext context) {

}
