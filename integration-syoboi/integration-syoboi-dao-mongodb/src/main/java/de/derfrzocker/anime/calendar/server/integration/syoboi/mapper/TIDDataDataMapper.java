package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TIDDataDO;

public final class TIDDataDataMapper {

    private TIDDataDataMapper() {
    }

    public static TIDDataDO toData(TIDData domain) {
        TIDDataDO data = new TIDDataDO();

        data.tid = domain.tid();
        data.title = domain.title();
        data.trackingChannel = domain.trackingChannel();
        data.firstStart = domain.firstStart();
        data.firstEnd = domain.firstEnd();
        data.firstChannels = domain.firstChannels();
        data.include = domain.include();
        data.validUntil = domain.validUntil();
        data.apply(domain);

        return data;
    }

    public static TIDData toDomain(TIDDataDO data) {
        return new TIDData(data.tid,
                           data.createdAt,
                           data.createdBy,
                           data.updatedAt,
                           data.updatedBy,
                           data.title,
                           data.trackingChannel,
                           data.firstStart,
                           data.firstEnd,
                           data.firstChannels,
                           data.include,
                           data.validUntil);
    }
}
