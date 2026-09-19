package de.derfrzocker.anime.calendar.season.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.season.dao.AnimeSeasonInfoDao;
import de.derfrzocker.anime.calendar.season.service.AnimeSeasonInfoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.season.exception.AnimeSeasonInfoExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.season.exception.AnimeSeasonInfoExceptions.inconsistentNotFound;

@ApplicationScoped
public class AnimeSeasonInfoServiceImpl implements AnimeSeasonInfoService {

    @Inject
    AnimeSeasonInfoDao dao;

    @Override
    public Optional<AnimeSeasonInfo> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             int year,
                                             Season season,
                                             RequestContext context) {
        return this.dao.getById(integrationId, integrationAnimeId, year, season, context);
    }

    @Override
    public AnimeSeasonInfo createWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          int year,
                                          Season season,
                                          AnimeSeasonInfoCreateData createData,
                                          RequestContext context) {
        Optional<AnimeSeasonInfo> optional = getById(integrationId, integrationAnimeId, year, season, context);
        if (optional.isPresent()) {
            throw alreadyCreated(integrationId, integrationAnimeId, year, season).get();
        }

        AnimeSeasonInfo info = AnimeSeasonInfo.from(integrationId,
                                                    integrationAnimeId,
                                                    year,
                                                    season,
                                                    createData,
                                                    context);

        this.dao.create(info, context);

        return getById(integrationId, integrationAnimeId, year, season, context).orElseThrow(inconsistentNotFound(
                integrationId,
                integrationAnimeId,
                year,
                season));
    }
}
