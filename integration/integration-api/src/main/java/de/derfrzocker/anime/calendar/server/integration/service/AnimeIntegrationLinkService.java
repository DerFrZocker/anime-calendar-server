package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeIntegrationLinkService {

    Stream<AnimeIntegrationLink> getAllWithId(IntegrationId integrationId,
                                              IntegrationAnimeId integrationAnimeId,
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

    AnimeIntegrationLink updateWithData(AnimeId animeId,
                                        IntegrationId integrationId,
                                        IntegrationAnimeId integrationAnimeId,
                                        AnimeIntegrationLinkUpdateData updateData,
                                        RequestContext context);

    void deleteById(AnimeId animeId,
                    IntegrationId integrationId,
                    IntegrationAnimeId integrationAnimeId,
                    RequestContext context);
}
