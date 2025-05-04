package de.derfrzocker.anime.calendar.server.integration.myanimelist.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import java.time.Year;

public record MyAnimeListSeasonInfo(IntegrationAnimeId animeId, Year year, Season season, AnimeName name) {

}
