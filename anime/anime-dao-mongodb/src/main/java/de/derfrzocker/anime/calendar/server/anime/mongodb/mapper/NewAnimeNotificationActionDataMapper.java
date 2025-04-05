package de.derfrzocker.anime.calendar.server.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.NewAnimeNotificationActionDO;
import java.util.HashMap;
import java.util.Map;

public final class NewAnimeNotificationActionDataMapper {

    private NewAnimeNotificationActionDataMapper() {
    }

    public static NewAnimeNotificationActionDO toData(NewAnimeNotificationAction domain) {
        NewAnimeNotificationActionDO data = new NewAnimeNotificationActionDO();

        data.id = domain.id();
        data.title = domain.title();
        data.episodeCount = domain.episodeCount();
        data.score = domain.score();
        data.links = toData(domain.links());
        data.apply(domain);

        return data;
    }

    public static NewAnimeNotificationAction toDomain(NewAnimeNotificationActionDO data) {
        return new NewAnimeNotificationAction(data.id,
                                              data.createdAt,
                                              data.createdBy,
                                              data.updatedAt,
                                              data.updatedBy,
                                              data.title,
                                              data.episodeCount,
                                              data.score,
                                              toDomain(data.links));
    }

    private static Map<String, String> toData(Map<IntegrationId, IntegrationAnimeId> domain) {
        Map<String, String> data = new HashMap<>();

        domain.forEach((key, value) -> data.put(key.raw(), value.raw()));

        return data;
    }

    private static Map<IntegrationId, IntegrationAnimeId> toDomain(Map<String, String> data) {
        Map<IntegrationId, IntegrationAnimeId> domain = new HashMap<>();

        data.forEach((key, value) -> domain.put(new IntegrationId(key), new IntegrationAnimeId(value)));

        return domain;
    }
}
