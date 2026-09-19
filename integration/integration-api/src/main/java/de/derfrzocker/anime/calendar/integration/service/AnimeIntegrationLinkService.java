package de.derfrzocker.anime.calendar.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLinkCreateData;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeIntegrationLinkService {

    Stream<AnimeIntegrationLink> getAllWithId(IntegrationId integrationId,
                                              IntegrationAnimeId integrationAnimeId,
                                              RequestContext context);

    Stream<AnimeIntegrationLink> getAllWithIds(IntegrationId integrationId,
                                               Collection<IntegrationAnimeId> integrationAnimeIds,
                                               RequestContext context);

    Stream<AnimeIntegrationLink> getAllWithId(AnimeId animeId, IntegrationId integrationId, RequestContext context);

    Optional<AnimeIntegrationLink> getById(AnimeId animeId,
                                           IntegrationId integrationId,
                                           IntegrationAnimeId integrationAnimeId,
                                           RequestContext context);

    AnimeIntegrationLink createWithData(AnimeId animeId,
                                        IntegrationId integrationId,
                                        IntegrationAnimeId integrationAnimeId,
                                        AnimeIntegrationLinkCreateData createData,
                                        RequestContext context);
}
