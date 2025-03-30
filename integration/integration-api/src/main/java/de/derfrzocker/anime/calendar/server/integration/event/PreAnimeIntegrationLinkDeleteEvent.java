package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;

public record PreAnimeIntegrationLinkDeleteEvent(AnimeIntegrationLink animeIntegrationLink, RequestContext context) {

}
