package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
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
}
