package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarTO;

public final class CalendarDomain {

    public static CalendarTO toTransfer(Calendar domain) {
        return new CalendarTO(domain.id(), domain.key(), domain.owner(), domain.name());
    }

    private CalendarDomain() {

    }
}
