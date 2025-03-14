package de.derfrzocker.anime.calendar.server.integration.syoboi.mapper;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import java.time.YearMonth;
import java.util.List;

public final class ProvidedTIDDataDataMapper {

    private ProvidedTIDDataDataMapper() {
    }

    public static ProvidedTIDData toDomain(TID tid,
                                           String title,
                                           YearMonth firstStart,
                                           YearMonth firstEnd,
                                           List<ChannelId> firstChannelIds) {
        return new ProvidedTIDData(tid, title, firstStart, firstEnd, firstChannelIds);
    }
}
