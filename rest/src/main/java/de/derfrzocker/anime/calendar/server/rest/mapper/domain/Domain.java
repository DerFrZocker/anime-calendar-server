package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.AnimeOverrideTO;
import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarTO;
import de.derfrzocker.anime.calendar.server.rest.transfer.user.UserTO;

public final class Domain {

    private Domain() {
    }

    public static UserTO toTransfer(User domain) {
        return UserDomainMapper.toTransfer(domain);
    }

    public static CalendarTO toTransfer(Calendar domain) {
        return CalendarDomainMapper.toTransfer(domain);
    }

    public static AnimeOverrideTO toTransfer(AnimeOverride domain) {
        return CalendarDomainMapper.toTransfer(domain);
    }
}
