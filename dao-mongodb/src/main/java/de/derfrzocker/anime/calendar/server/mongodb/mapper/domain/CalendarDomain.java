package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarDO;

public final class CalendarDomain {

    public static CalendarDO toData(Calendar domain) {
        CalendarDO data = new CalendarDO();

        data.id = domain.id();
        data.key = domain.key();
        data.owner = domain.owner();
        data.name = domain.name();
        data.apply(domain);

        return data;
    }

    private CalendarDomain() {

    }
}
