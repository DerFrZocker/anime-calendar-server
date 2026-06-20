package de.derfrzocker.anime.calendar.server.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.NewAnimeNotificationActionDO;

public final class NewAnimeNotificationActionDataMapper {

    private NewAnimeNotificationActionDataMapper() {
    }

    public static NewAnimeNotificationActionDO toData(NewAnimeNotificationAction domain) {
        NewAnimeNotificationActionDO data = new NewAnimeNotificationActionDO();

        data.id = domain.id();
        data.title = domain.title();
        data.episodeCount = domain.episodeCount();
        data.score = domain.score();
        data.sourceIntegrationId = domain.sourceIntegrationId();
        data.sourceIntegrationAnimeId = domain.sourceIntegrationAnimeId();
        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.apply(domain);

        return data;
    }

    public static NewAnimeNotificationAction toDomain(NewAnimeNotificationActionDO data) {
        return new NewAnimeNotificationAction(
                data.id,
                data.createdAt,
                data.createdBy,
                data.updatedAt,
                data.updatedBy,
                data.title,
                data.episodeCount,
                data.score,
                data.sourceIntegrationId,
                data.sourceIntegrationAnimeId,
                data.integrationId,
                data.integrationAnimeId);
    }
}
