package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import java.time.Instant;

public final class ProvidedAnimeScheduleDataMapper {

    private ProvidedAnimeScheduleDataMapper() {
    }

    public static ProvidedAnimeSchedule toDomain(TID tid,
                                                 Channel channel,
                                                 int episode,
                                                 Instant startTime,
                                                 Instant endTime) {
        return new ProvidedAnimeSchedule(tid, channel, episode, startTime, endTime);
    }
}
