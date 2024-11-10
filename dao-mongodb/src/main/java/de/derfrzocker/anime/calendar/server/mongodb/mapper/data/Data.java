package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.mongodb.data.AnimeOverrideDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.CalendarDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

public final class Data {

    private Data() {
    }

    public static User toDomain(UserDO data) {
        return UserDataMapper.toDomain(data);
    }

    public static Calendar toDomain(CalendarDO data) {
        return CalendarDataMapper.toDomain(data);
    }

    public static AnimeOverride toDomain(AnimeOverrideDO data) {
        return CalendarDataMapper.toDomain(data);
    }
}
