package de.derfrzocker.anime.calendar.server.integration.myanimelist.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.api.MyAnimeListSeasonInfo;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.dao.MyAnimeListSeasonInfoDao;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.servce.MyAnimeListSeasonInfoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Year;
import java.util.List;

@ApplicationScoped
public class MyAnimeListSeasonInfoServiceImpl implements MyAnimeListSeasonInfoService {

    @Inject
    MyAnimeListSeasonInfoDao dao;

    @Override
    public List<MyAnimeListSeasonInfo> getAllByYearAndSeason(Year year, Season season, RequestContext context) {
        return this.dao.getAllByYearAndSeason(year, season, context);
    }
}
