package de.derfrzocker.anime.calendar.server.integration.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record AnimeIntegrationLink(AnimeId animeId, IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                   Instant createdAt, UserId createdBy, Instant updatedAt,
                                   UserId updatedBy) implements ModificationInfo {

    public static AnimeIntegrationLink from(AnimeId animeId,
                                            IntegrationId integrationId,
                                            IntegrationAnimeId integrationAnimeId,
                                            RequestContext context) {
        return new AnimeIntegrationLink(animeId,
                                        integrationId,
                                        integrationAnimeId,
                                        context.requestTime(),
                                        context.requestUser(),
                                        context.requestTime(),
                                        context.requestUser());
    }

    public AnimeIntegrationLink updateWithData(AnimeIntegrationLinkUpdateData updateData, RequestContext context) {
        return new AnimeIntegrationLink(animeId(),
                                        integrationId(),
                                        integrationAnimeId(),
                                        createdAt(),
                                        createdBy(),
                                        context.requestTime(),
                                        context.requestUser());
    }
}
