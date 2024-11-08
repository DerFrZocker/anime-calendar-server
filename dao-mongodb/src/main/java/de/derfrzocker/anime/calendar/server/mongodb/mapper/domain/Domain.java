package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

public final class Domain {

    private Domain() {
    }

    public static UserDO toData(UserCreateData userCreateData) {
        return UserDomainMapper.toData(userCreateData);
    }
}
