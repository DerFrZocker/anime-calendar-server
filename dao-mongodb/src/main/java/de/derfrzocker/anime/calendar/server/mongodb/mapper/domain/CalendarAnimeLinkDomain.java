package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarAnimeLinkDO;

public final class CalendarAnimeLinkDomain {

    public static CalendarAnimeLinkDO toData(CalendarAnimeLink domain) {
        CalendarAnimeLinkDO data = new CalendarAnimeLinkDO();

        data.calendarId = domain.calendarId();
        data.animeId = domain.animeId();
        data.include = domain.include();
        data.apply(domain);

        return data;
    }

    private CalendarAnimeLinkDomain() {

    }
}
