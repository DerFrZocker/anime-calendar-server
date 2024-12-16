package de.derfrzocker.anime.calendar.server.core.api.integration;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
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
}
