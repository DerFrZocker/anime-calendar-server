package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeIntegrationLinkDao {

    Stream<AnimeIntegrationLink> getAllWithId(IntegrationId integrationId,
                                              IntegrationAnimeId integrationAnimeId,
                                              RequestContext context);

    Stream<AnimeIntegrationLink> getAllWithId(AnimeId animeId, IntegrationId integrationId, RequestContext context);

    Optional<AnimeIntegrationLink> getById(AnimeId animeId,
                                           IntegrationId integrationId,
                                           IntegrationAnimeId integrationAnimeId,
                                           RequestContext context);

    void create(AnimeIntegrationLink link, RequestContext context);

    void update(AnimeIntegrationLink link, RequestContext context);

    void delete(AnimeIntegrationLink link, RequestContext context);
}
