package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

final class UserDomainMapper {

    private UserDomainMapper() {
    }

    static UserDO toData(UserCreateData userCreateData) {
        UserDO userDO = new UserDO();

        userDO.userId = userCreateData.userId();
        userDO.hashedUserToken = userCreateData.hashedUserToken();

        return userDO;
    }
}
