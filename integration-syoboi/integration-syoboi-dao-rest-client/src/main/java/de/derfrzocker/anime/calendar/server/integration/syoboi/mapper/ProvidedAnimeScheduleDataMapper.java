package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import java.time.Instant;

public final class ProvidedAnimeScheduleDataMapper {

    private ProvidedAnimeScheduleDataMapper() {
    }

    public static ProvidedAnimeSchedule toDomain(TID tid,
                                                 ChannelId channelId,
                                                 int episode,
                                                 Instant startTime,
                                                 Instant endTime) {
        return new ProvidedAnimeSchedule(tid, channelId, episode, startTime, endTime);
    }
}
