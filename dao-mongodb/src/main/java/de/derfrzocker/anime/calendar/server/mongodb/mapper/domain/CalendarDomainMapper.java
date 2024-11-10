package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.AnimeOverrideDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.CalendarDO;
import java.util.HashSet;
import java.util.stream.Collectors;

final class CalendarDomainMapper {

    private CalendarDomainMapper() {
    }

    static CalendarDO toData(CalendarCreateData calendarCreateData) {
        CalendarDO calendarDO = new CalendarDO();

        calendarDO.id = calendarCreateData.id();
        calendarDO.key = calendarCreateData.key();
        calendarDO.owner = calendarCreateData.owner();
        calendarDO.animeOverrides = new HashSet<>();

        return calendarDO;
    }

    public static CalendarDO toData(Calendar domain, CalendarChangeData calendarChangeData) {
        CalendarDO calendarDO = new CalendarDO();

        calendarDO.id = domain.id();
        calendarDO.key = domain.key();
        calendarDO.createdAt = domain.createdAt();
        calendarDO.owner = domain.owner();
        calendarDO.animeOverrides = calendarChangeData.animeOverrides()
                                                      .apply(domain.animeOverrides())
                                                      .values()
                                                      .stream()
                                                      .map(Domain::toData)
                                                      .collect(Collectors.toSet());

        return calendarDO;
    }

    public static AnimeOverrideDO toData(AnimeOverride domain) {
        return new AnimeOverrideDO(domain.id(), domain.animeId(), domain.include());
    }
}
