package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

public final class Data {

    private Data() {
    }

    public static User toDomain(UserDO data) {
        return UserDataMapper.toDomain(data);
    }
}
