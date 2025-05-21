package de.derfrzocker.anime.calendar.server.season.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeSeasonInfoDao {

    Stream<AnimeSeasonInfo> getAll(RequestContext context);

    Optional<AnimeSeasonInfo> getById(IntegrationId integrationId,
                                      IntegrationAnimeId integrationAnimeId,
                                      int year,
                                      Season season,
                                      RequestContext context);

    void create(AnimeSeasonInfo animeSeasonInfo, RequestContext context);

    void update(AnimeSeasonInfo animeSeasonInfo, RequestContext context);

    void delete(AnimeSeasonInfo animeSeasonInfo, RequestContext context);
}
