package de.derfrzocker.anime.calendar.server.rest.mapper.transfer;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverrideCreateData;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.AnimeOverrideCreateDataTO;

public final class Transfer {

    private Transfer() {
    }

    public static AnimeOverrideCreateData toDomain(AnimeOverrideCreateDataTO transfer) {
        return CalendarTransferMapper.toDomain(transfer);
    }
}
