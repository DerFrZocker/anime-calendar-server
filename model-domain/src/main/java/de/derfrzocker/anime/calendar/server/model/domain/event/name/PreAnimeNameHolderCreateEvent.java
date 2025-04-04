package de.derfrzocker.anime.calendar.server.model.domain.event.name;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;

public record PreAnimeNameHolderCreateEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                            AnimeNameHolderCreateData createData, AnimeNameHolder animeNameHolder,
                                            RequestContext context) {

}
