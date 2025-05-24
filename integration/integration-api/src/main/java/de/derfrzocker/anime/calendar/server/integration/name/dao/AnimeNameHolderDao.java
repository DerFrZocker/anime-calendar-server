package de.derfrzocker.anime.calendar.server.integration.name.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeNameHolderDao {

    Stream<AnimeNameHolder> getAll(RequestContext context);

    Stream<AnimeNameHolder> getAllWithId(IntegrationId integrationId, RequestContext context);

    Optional<AnimeNameHolder> getById(IntegrationId integrationId,
                                      IntegrationAnimeId integrationAnimeId,
                                      RequestContext context);

    void create(AnimeNameHolder animeNameHolder, RequestContext context);

    void update(AnimeNameHolder animeNameHolder, RequestContext context);

    void delete(AnimeNameHolder animeNameHolder, RequestContext context);
}
