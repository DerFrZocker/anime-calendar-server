package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.mongodb.data.AnimeOverrideDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.CalendarDO;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

final class CalendarDataMapper {

    private CalendarDataMapper() {

    }

    static Calendar toDomain(CalendarDO data) {
        Map<AnimeOverrideID, AnimeOverride> animeOverrides = new HashMap<>();
        data.animeOverrides.stream().map(Data::toDomain).forEach(a -> animeOverrides.put(a.id(), a));

        return new Calendar(data.id,
                            data.key,
                            data.createdAt,
                            data.owner,
                            new HashSet<>(),// TODO 2024-11-10: Add this
                            animeOverrides);
    }

    static AnimeOverride toDomain(AnimeOverrideDO data) {
        return new AnimeOverride(data.id(), data.animeId(), data.include());
    }
}
