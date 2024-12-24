package de.derfrzocker.anime.calendar.server.core.api.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
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
