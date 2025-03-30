package de.derfrzocker.anime.calendar.server.core.api.season;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoUpdateData;
import de.derfrzocker.anime.calendar.core.season.Season;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeSeasonInfoService {

    Stream<AnimeSeasonInfo> getAll(RequestContext context);

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

    AnimeSeasonInfo updateWithData(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   int year,
                                   Season season,
                                   AnimeSeasonInfoUpdateData updateData,
                                   RequestContext context);

    void deleteById(IntegrationId integrationId,
                    IntegrationAnimeId integrationAnimeId,
                    int year,
                    Season season,
                    RequestContext context);
}
