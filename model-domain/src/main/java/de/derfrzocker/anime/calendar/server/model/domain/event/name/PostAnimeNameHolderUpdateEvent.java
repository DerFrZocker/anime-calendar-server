package de.derfrzocker.anime.calendar.server.model.domain.event.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;

public record PostAnimeNameHolderUpdateEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                             AnimeNameHolderUpdateData updateData, AnimeNameHolder current,
                                             AnimeNameHolder updated, RequestContext context) {

}
