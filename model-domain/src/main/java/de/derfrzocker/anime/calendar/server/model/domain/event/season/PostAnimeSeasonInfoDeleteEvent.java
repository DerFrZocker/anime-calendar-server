package de.derfrzocker.anime.calendar.server.model.domain.event.season;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.core.season.Season;

public record PostAnimeSeasonInfoDeleteEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                             int year, Season season, AnimeSeasonInfo animeSeasonInfo,
                                             RequestContext context) {

}
