package de.derfrzocker.anime.calendar.server.core.api.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
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
