package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PreAnimeIntegrationLinkUpdateEvent(AnimeIntegrationLink current, AnimeIntegrationLink updated,
                                                 AnimeIntegrationLinkUpdateData updateData, RequestContext context) {

}
