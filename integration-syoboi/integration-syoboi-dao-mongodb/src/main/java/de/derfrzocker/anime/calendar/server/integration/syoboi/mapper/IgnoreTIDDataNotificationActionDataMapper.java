package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.IgnoreTIDDataNotificationActionDO;

public final class IgnoreTIDDataNotificationActionDataMapper {

    private IgnoreTIDDataNotificationActionDataMapper() {
    }

    public static IgnoreTIDDataNotificationActionDO toData(IgnoreTIDDataNotificationAction domain) {
        IgnoreTIDDataNotificationActionDO data = new IgnoreTIDDataNotificationActionDO();

        data.id = domain.id();
        data.tid = domain.tid();
        data.apply(domain);

        return data;
    }

    public static IgnoreTIDDataNotificationAction toDomain(IgnoreTIDDataNotificationActionDO data) {
        return new IgnoreTIDDataNotificationAction(data.id,
                                                   data.createdAt,
                                                   data.createdBy,
                                                   data.updatedAt,
                                                   data.updatedBy,
                                                   data.tid);
    }
}
