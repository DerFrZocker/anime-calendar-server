package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarAnimeLinkTO;

public final class CalendarAnimeLinkDomain {

    public static CalendarAnimeLinkTO toTransfer(CalendarAnimeLink domain) {
        return new CalendarAnimeLinkTO(domain.calendarId(), domain.animeId(), domain.include());
    }

    private CalendarAnimeLinkDomain() {

    }
}
