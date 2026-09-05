package de.derfrzocker.anime.calendar.integration.myanimelist.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeName;
import java.time.Year;

public record MyAnimeListSeasonInfo(IntegrationAnimeId animeId, Year year, Season season, AnimeName name) {

}
