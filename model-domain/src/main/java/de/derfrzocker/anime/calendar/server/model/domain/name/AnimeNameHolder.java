package de.derfrzocker.anime.calendar.server.model.domain.name;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;
import java.util.List;

public record AnimeNameHolder(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, Instant createdAt,
                              UserId createdBy, Instant updatedAt, UserId updatedBy,
                              List<AnimeName> names) implements ModificationInfo {

    public static AnimeNameHolder from(IntegrationId integrationId,
                                       IntegrationAnimeId integrationAnimeId,
                                       AnimeNameHolderCreateData createData,
                                       RequestContext context) {
        return new AnimeNameHolder(integrationId,
                                   integrationAnimeId,
                                   context.requestTime(),
                                   context.requestUser(),
                                   context.requestTime(),
                                   context.requestUser(),
                                   createData.names());
    }

    public AnimeNameHolder updateWithData(AnimeNameHolderUpdateData updateData, RequestContext context) {
        return new AnimeNameHolder(integrationId(),
                                   integrationAnimeId(),
                                   createdAt(),
                                   createdBy(),
                                   context.requestTime(),
                                   context.requestUser(),
                                   updateData.names().apply(names()));
    }
}
