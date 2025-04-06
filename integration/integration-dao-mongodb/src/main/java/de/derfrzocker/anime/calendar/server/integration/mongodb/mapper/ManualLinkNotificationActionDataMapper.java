package de.derfrzocker.anime.calendar.server.integration.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.mongodb.data.ManualLinkNotificationActionDO;

public final class ManualLinkNotificationActionDataMapper {

    private ManualLinkNotificationActionDataMapper() {
    }

    public static ManualLinkNotificationActionDO toData(ManualLinkNotificationAction domain) {
        ManualLinkNotificationActionDO data = new ManualLinkNotificationActionDO();

        data.id = domain.id();
        data.animeId = domain.animeId();
        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.apply(domain);

        return data;
    }

    public static ManualLinkNotificationAction toDomain(ManualLinkNotificationActionDO data) {
        return new ManualLinkNotificationAction(data.id,
                                                data.createdAt,
                                                data.createdBy,
                                                data.updatedAt,
                                                data.updatedBy,
                                                data.animeId,
                                                data.integrationId,
                                                data.integrationAnimeId);
    }
}
