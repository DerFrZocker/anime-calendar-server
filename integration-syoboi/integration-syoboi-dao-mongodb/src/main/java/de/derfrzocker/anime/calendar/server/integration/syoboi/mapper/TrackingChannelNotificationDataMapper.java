package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TrackingChannelNotificationDO;

public final class TrackingChannelNotificationDataMapper {

    private TrackingChannelNotificationDataMapper() {
    }

    public static TrackingChannelNotificationDO toData(TrackingChannelNotification domain) {
        return new TrackingChannelNotificationDO(
                domain.id(),
                domain.tid(),
                domain.title(),
                domain.currentChannelId(),
                domain.currentChannelName());
    }

    public static TrackingChannelNotification toDomain(TrackingChannelNotificationDO data) {
        return new TrackingChannelNotification(
                data.id(),
                data.tid(),
                data.title(),
                data.currentChannelId(),
                data.currentChannelName());
    }
}
