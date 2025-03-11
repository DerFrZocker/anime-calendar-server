package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TrackingChannelNotificationActionDO;

public final class TrackingChannelNotificationActionDataMapper {

    private TrackingChannelNotificationActionDataMapper() {
    }

    public static TrackingChannelNotificationActionDO toData(TrackingChannelNotificationAction domain) {
        TrackingChannelNotificationActionDO data = new TrackingChannelNotificationActionDO();

        data.id = domain.id();
        data.tid = domain.tid();
        data.title = domain.title();
        data.channel = domain.channel();
        data.apply(domain);

        return data;
    }

    public static TrackingChannelNotificationAction toDomain(TrackingChannelNotificationActionDO data) {
        return new TrackingChannelNotificationAction(data.id,
                                                     data.createdAt,
                                                     data.createdBy,
                                                     data.updatedAt,
                                                     data.updatedBy,
                                                     data.tid,
                                                     data.title,
                                                     data.channel);
    }
}
