package de.derfrzocker.anime.calendar.server.notify.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.mongodb.data.NotificationDO;

public final class NotificationDataMapper {

    private NotificationDataMapper() {
    }

    public static NotificationDO toData(Notification domain) {
        NotificationDO data = new NotificationDO();

        data.id = domain.id();
        data.type = domain.type();
        data.validUntil = domain.validUntil();
        data.apply(domain);

        return data;
    }

    public static Notification toDomain(NotificationDO data) {
        return new Notification(data.id,
                                data.createdAt,
                                data.createdBy,
                                data.updatedAt,
                                data.updatedBy,
                                data.type,
                                data.validUntil);
    }
}
