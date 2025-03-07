package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeIntegrationLinkDao {

    Stream<AnimeIntegrationLink> getAllWithId(IntegrationId integrationId,
                                              IntegrationAnimeId integrationAnimeId,
                                              RequestContext context);

    Optional<AnimeIntegrationLink> getById(AnimeId animeId,
                                           IntegrationId integrationId,
                                           IntegrationAnimeId integrationAnimeId,
                                           RequestContext context);

    void create(AnimeIntegrationLink link, RequestContext context);

    void update(AnimeIntegrationLink link, RequestContext context);

    void delete(AnimeIntegrationLink link, RequestContext context);
}
