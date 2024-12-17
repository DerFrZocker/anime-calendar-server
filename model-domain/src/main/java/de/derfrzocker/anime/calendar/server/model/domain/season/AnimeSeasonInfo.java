package de.derfrzocker.anime.calendar.server.model.domain.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.time.Instant;

public record AnimeSeasonInfo(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, int year,
                              Season season, Instant createdAt, UserId createdBy, Instant updatedAt,
                              UserId updatedBy) implements ModificationInfo {

    public static AnimeSeasonInfo from(IntegrationId integrationId,
                                       IntegrationAnimeId integrationAnimeId,
                                       int year,
                                       Season season,
                                       AnimeSeasonInfoCreateData createData,
                                       RequestContext context) {
        return new AnimeSeasonInfo(integrationId,
                                   integrationAnimeId,
                                   year,
                                   season,
                                   context.requestTime(),
                                   context.requestUser(),
                                   context.requestTime(),
                                   context.requestUser());
    }

    public AnimeSeasonInfo updateWithData(AnimeSeasonInfoUpdateData updateData, RequestContext context) {
        return new AnimeSeasonInfo(integrationId(),
                                   integrationAnimeId(),
                                   year(),
                                   season(),
                                   createdAt(),
                                   createdBy(),
                                   context.requestTime(),
                                   context.requestUser());
    }
}
