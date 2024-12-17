package de.derfrzocker.anime.calendar.server.model.domain.event.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;

public record PreAnimeSeasonInfoDeleteEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                            int year, Season season, AnimeSeasonInfo animeSeasonInfo,
                                            RequestContext context) {

}
