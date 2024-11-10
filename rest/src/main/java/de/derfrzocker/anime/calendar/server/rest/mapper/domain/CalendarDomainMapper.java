package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.AnimeOverrideTO;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarTO;
import java.util.stream.Collectors;

final class CalendarDomainMapper {

    private CalendarDomainMapper() {
    }

    static CalendarTO toTransfer(Calendar domain) {
        return new CalendarTO(domain.id(),
                              domain.key(),
                              domain.animeOverrides()
                                    .values()
                                    .stream()
                                    .map(Domain::toTransfer)
                                    .collect(Collectors.toSet()));
    }

    static AnimeOverrideTO toTransfer(AnimeOverride domain) {
        return new AnimeOverrideTO(domain.id(), domain.animeId(), domain.include());
    }
}
