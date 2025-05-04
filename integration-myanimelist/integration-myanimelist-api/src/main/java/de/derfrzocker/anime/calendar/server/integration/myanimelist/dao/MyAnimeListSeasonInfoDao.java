package de.derfrzocker.anime.calendar.server.integration.myanimelist.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.api.MyAnimeListSeasonInfo;
import java.time.Year;
import java.util.List;

public interface MyAnimeListSeasonInfoDao {

    List<MyAnimeListSeasonInfo> getAllByYearAndSeason(Year year, Season season, RequestContext context);
}
