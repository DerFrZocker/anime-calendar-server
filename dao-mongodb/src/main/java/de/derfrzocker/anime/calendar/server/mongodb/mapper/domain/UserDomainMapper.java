package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;
import java.util.HashSet;

final class UserDomainMapper {

    private UserDomainMapper() {
    }

    static UserDO toData(UserCreateData userCreateData) {
        UserDO userDO = new UserDO();

        userDO.id = userCreateData.id();
        userDO.hashedToken = userCreateData.hashedToken();
        userDO.calendars = new HashSet<>();
        userDO.animeAccountLinks = new HashSet<>();

        return userDO;
    }

    public static UserDO toData(User domain, UserChangeData userChangeData) {
        UserDO userDO = new UserDO();

        userDO.id = domain.id();
        userDO.createdAt = domain.createdAt();
        userDO.hashedToken = domain.hashedToken();
        userDO.calendars = userChangeData.calendars().apply(domain.calendars());
        userDO.animeAccountLinks = domain.animeAccountLinks();

        return userDO;
    }
}
