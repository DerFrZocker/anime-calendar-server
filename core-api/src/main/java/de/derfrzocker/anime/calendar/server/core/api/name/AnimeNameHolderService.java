package de.derfrzocker.anime.calendar.server.core.api.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeNameHolderService {

    Stream<AnimeNameHolder> getAll(RequestContext context);

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
