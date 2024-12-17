package de.derfrzocker.anime.calendar.server.model.domain.event.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;

public record PostAnimeSeasonInfoCreateEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                             int year, Season season, AnimeSeasonInfoCreateData createData,
                                             AnimeSeasonInfo animeSeasonInfo, RequestContext context) {

}
