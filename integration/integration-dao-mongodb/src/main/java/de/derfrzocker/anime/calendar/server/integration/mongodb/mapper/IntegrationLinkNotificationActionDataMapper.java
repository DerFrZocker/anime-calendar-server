package de.derfrzocker.anime.calendar.server.integration.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.mongodb.data.IntegrationLinkNotificationActionDO;

public final class IntegrationLinkNotificationActionDataMapper {

    private IntegrationLinkNotificationActionDataMapper() {
    }

    public static IntegrationLinkNotificationActionDO toData(IntegrationLinkNotificationAction domain) {
        IntegrationLinkNotificationActionDO data = new IntegrationLinkNotificationActionDO();

        data.id = domain.id();
        data.animeId = domain.animeId();
        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.score = domain.score();
        data.bestName = domain.bestName();
        data.apply(domain);

        return data;
    }

    public static IntegrationLinkNotificationAction toDomain(IntegrationLinkNotificationActionDO data) {
        return new IntegrationLinkNotificationAction(data.id,
                                                     data.createdAt,
                                                     data.createdBy,
                                                     data.updatedAt,
                                                     data.updatedBy,
                                                     data.animeId,
                                                     data.integrationId,
                                                     data.integrationAnimeId,
                                                     data.score,
                                                     data.bestName);
    }
}
