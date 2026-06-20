package de.derfrzocker.anime.calendar.server.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.NewAnimeNotificationDO;

public final class NewAnimeNotificationDataMapper {

    private NewAnimeNotificationDataMapper() {
    }

    public static NewAnimeNotificationDO toData(NewAnimeNotification domain) {
        return new NewAnimeNotificationDO(domain.id(), domain.integrationId(), domain.integrationAnimeId());
    }

    public static NewAnimeNotification toDomain(NewAnimeNotificationDO data) {
        return new NewAnimeNotification(data.id(), data.integrationId(), data.integrationAnimeId());
    }
}
