package de.derfrzocker.anime.calendar.server.model.domain.event.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;

public record PreAnimeNameHolderDeleteEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                            AnimeNameHolder animeNameHolder, RequestContext context) {

}