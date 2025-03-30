package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkUpdateData;

public record PostAnimeIntegrationLinkUpdateEvent(AnimeIntegrationLink current, AnimeIntegrationLink updated,
                                                  AnimeIntegrationLinkUpdateData updateData, RequestContext context) {

}
