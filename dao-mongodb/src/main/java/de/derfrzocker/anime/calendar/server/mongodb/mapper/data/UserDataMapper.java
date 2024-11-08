package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

final class UserDataMapper {

    private UserDataMapper() {

    }

    static User toDomain(UserDO data) {
        return new User(data.userId, data.createdAt, data.hashedUserToken, data.calendars, data.animeAccountLinks);
    }
}
