package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.mapper;

import de.derfrzocker.anime.calendar.server.integration.mongodb.notify.data.StreamingNotificationDO;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;

public final class StreamingNotificationDataMapper {

    private StreamingNotificationDataMapper() {
    }

    public static StreamingNotificationDO toData(StreamingNotification domain) {
        StreamingNotificationDO data = new StreamingNotificationDO();

        data.id = domain.id();
        data.animeId = domain.animeId();
        data.orgEpisodeIndex = domain.orgEpisodeIndex();
        data.orgStreamingTime = domain.orgStreamingTime();
        data.name = domain.name();
        data.referenceIntegrationId = domain.referenceIntegrationId();
        data.referenceIntegrationAnimeId = domain.referenceIntegrationAnimeId();
        data.apply(domain);

        return data;
    }

    public static StreamingNotification toDomain(StreamingNotificationDO data) {
        return new StreamingNotification(data.id,
                                         data.createdAt,
                                         data.createdBy,
                                         data.updatedAt,
                                         data.updatedBy,
                                         data.animeId,
                                         data.orgEpisodeIndex,
                                         data.orgStreamingTime,
                                         data.name,
                                         data.referenceIntegrationId,
                                         data.referenceIntegrationAnimeId);
    }
}
