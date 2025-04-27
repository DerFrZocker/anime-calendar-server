package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.mapper;

import de.derfrzocker.anime.calendar.server.integration.mongodb.notify.data.StreamingNotificationActionDO;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;

public final class StreamingNotificationActionDataMapper {

    private StreamingNotificationActionDataMapper() {
    }

    public static StreamingNotificationActionDO toData(StreamingNotificationAction domain) {
        StreamingNotificationActionDO data = new StreamingNotificationActionDO();

        data.id = domain.id();
        data.animeId = domain.animeId();
        data.orgEpisodeIndex = domain.orgEpisodeIndex();
        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.streamingEpisode = domain.streamingEpisode();
        data.streamingTime = domain.streamingTime();
        data.apply(domain);

        return data;
    }

    public static StreamingNotificationAction toDomain(StreamingNotificationActionDO data) {
        return new StreamingNotificationAction(data.id,
                                               data.createdAt,
                                               data.createdBy,
                                               data.updatedAt,
                                               data.updatedBy,
                                               data.animeId,
                                               data.orgEpisodeIndex,
                                               data.integrationId,
                                               data.integrationAnimeId,
                                               data.streamingEpisode,
                                               data.streamingTime);
    }
}
