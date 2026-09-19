package de.derfrzocker.anime.calendar.season.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfoCreateData;

import java.util.Optional;

public interface AnimeSeasonInfoService {

    Optional<AnimeSeasonInfo> getById(IntegrationId integrationId,
                                      IntegrationAnimeId integrationAnimeId,
                                      int year,
                                      Season season,
                                      RequestContext context);

    AnimeSeasonInfo createWithData(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   int year,
                                   Season season,
                                   AnimeSeasonInfoCreateData createData,
                                   RequestContext context);
}
