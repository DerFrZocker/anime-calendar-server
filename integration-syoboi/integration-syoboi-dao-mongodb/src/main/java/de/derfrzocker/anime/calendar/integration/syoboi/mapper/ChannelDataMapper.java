package de.derfrzocker.anime.calendar.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.integration.syoboi.data.ChannelDO;

public final class ChannelDataMapper {

    private ChannelDataMapper() {
    }

    public static ChannelDO toData(Channel domain) {
        ChannelDO data = new ChannelDO();

        data.id = domain.id();
        data.name = domain.name();
        data.apply(domain);

        return data;
    }

    public static Channel toDomain(ChannelDO data) {
        return new Channel(data.id, data.createdAt, data.createdBy, data.updatedAt, data.updatedBy, data.name);
    }
}
