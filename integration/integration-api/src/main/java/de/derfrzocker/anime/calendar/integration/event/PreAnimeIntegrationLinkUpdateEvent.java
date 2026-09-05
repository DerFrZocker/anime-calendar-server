package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLinkUpdateData;

public record PreAnimeIntegrationLinkUpdateEvent(AnimeIntegrationLink current, AnimeIntegrationLink updated,
                                                 AnimeIntegrationLinkUpdateData updateData, RequestContext context) {

}
