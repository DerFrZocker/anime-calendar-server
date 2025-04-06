package de.derfrzocker.anime.calendar.server.notify.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.mongodb.data.NotificationActionDO;

public final class NotificationActionDataMapper {

    private NotificationActionDataMapper() {
    }

    public static NotificationActionDO toData(NotificationAction domain) {
        NotificationActionDO data = new NotificationActionDO();

        data.id = domain.id();
        data.notificationId = domain.notificationId();
        data.actionType = domain.actionType();
        data.requireUserInput = domain.requireUserInput();
        data.executedAt = domain.executedAt();
        data.executedBy = domain.executedBy();
        data.apply(domain);

        return data;
    }

    public static NotificationAction toDomain(NotificationActionDO data) {
        return new NotificationAction(data.id,
                                      data.createdAt,
                                      data.createdBy,
                                      data.updatedAt,
                                      data.updatedBy,
                                      data.notificationId,
                                      data.actionType,
                                      data.requireUserInput,
                                      data.executedAt,
                                      data.executedBy);
    }
}
