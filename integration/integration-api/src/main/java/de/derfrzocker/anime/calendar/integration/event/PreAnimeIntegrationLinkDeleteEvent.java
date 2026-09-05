package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;

public record PreAnimeIntegrationLinkDeleteEvent(AnimeIntegrationLink animeIntegrationLink, RequestContext context) {

}
