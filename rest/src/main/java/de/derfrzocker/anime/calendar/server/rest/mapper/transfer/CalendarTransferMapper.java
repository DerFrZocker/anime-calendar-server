package de.derfrzocker.anime.calendar.server.rest.mapper.transfer;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverrideCreateData;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.AnimeOverrideCreateDataTO;

final class CalendarTransferMapper {

    private CalendarTransferMapper() {
    }

    static AnimeOverrideCreateData toDomain(AnimeOverrideCreateDataTO transfer) {
        return new AnimeOverrideCreateData(transfer.animeId(), transfer.include());
    }
}
