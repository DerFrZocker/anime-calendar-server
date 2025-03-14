package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ChItemTDO;

public final class ProvidedChannelDataMapper {

    private ProvidedChannelDataMapper() {
    }

    public static ProvidedChannel toDomain(ChItemTDO data) {
        return new ProvidedChannel(new ChannelId(data.chId), data.chName);
    }
}
