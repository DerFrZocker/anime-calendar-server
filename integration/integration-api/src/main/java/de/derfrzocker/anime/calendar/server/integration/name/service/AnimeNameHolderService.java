package de.derfrzocker.anime.calendar.server.integration.name.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeNameHolderService {

    Stream<AnimeNameHolder> getAll(RequestContext context);

    Stream<AnimeNameHolder> getAllWithId(IntegrationId integrationId, RequestContext context);

    Optional<AnimeNameHolder> getById(IntegrationId integrationId,
                                      IntegrationAnimeId integrationAnimeId,
                                      RequestContext context);

    AnimeNameHolder createWithData(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   AnimeNameHolderCreateData createData,
                                   RequestContext context);

    AnimeNameHolder updateWithData(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   AnimeNameHolderUpdateData updateData,
                                   RequestContext context);

    void deleteById(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, RequestContext context);
}
