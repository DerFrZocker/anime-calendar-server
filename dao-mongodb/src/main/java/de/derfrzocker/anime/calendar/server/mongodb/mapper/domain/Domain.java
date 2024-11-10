package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.AnimeOverrideDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.CalendarDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

public final class Domain {

    private Domain() {
    }

    public static UserDO toData(UserCreateData userCreateData) {
        return UserDomainMapper.toData(userCreateData);
    }

    public static UserDO toData(User domain, UserChangeData userChangeData) {
        return UserDomainMapper.toData(domain, userChangeData);
    }

    public static CalendarDO toData(CalendarCreateData calendarCreateData) {
        return CalendarDomainMapper.toData(calendarCreateData);
    }

    public static CalendarDO toData(Calendar domain, CalendarChangeData calendarChangeData) {
        return CalendarDomainMapper.toData(domain, calendarChangeData);
    }

    public static AnimeOverrideDO toData(AnimeOverride domain) {
        return CalendarDomainMapper.toData(domain);
    }
}
