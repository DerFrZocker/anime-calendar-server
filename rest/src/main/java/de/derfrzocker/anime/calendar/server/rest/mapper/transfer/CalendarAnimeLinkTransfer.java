package de.derfrzocker.anime.calendar.server.rest.mapper.transfer;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.util.FixedChange;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarAnimeLinkCreateOrUpdateDataTO;

public final class CalendarAnimeLinkTransfer {

    public static CalendarAnimeLinkUpdateData toUpdateData(CalendarAnimeLinkCreateOrUpdateDataTO transfer) {
        return new CalendarAnimeLinkUpdateData(FixedChange.of(transfer.include()));
    }

    public static CalendarAnimeLinkCreateData toCreateData(CalendarAnimeLinkCreateOrUpdateDataTO transfer) {
        return new CalendarAnimeLinkCreateData(transfer.include());
    }

    private CalendarAnimeLinkTransfer() {

    }
}