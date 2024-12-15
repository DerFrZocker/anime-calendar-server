package de.derfrzocker.anime.calendar.server.rest.mapper.transfer;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarCreateDataTO;

public final class CalendarTransfer {

    public static CalendarCreateData toDomain(CalendarCreateDataTO transfer) {
        return new CalendarCreateData(transfer.owner(), transfer.name());
    }

    private CalendarTransfer() {

    }
}
