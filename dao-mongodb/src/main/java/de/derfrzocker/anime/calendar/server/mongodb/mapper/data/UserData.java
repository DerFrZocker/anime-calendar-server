package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;

public final class UserData {

    public static User toDomain(UserDO data) {
        return new User(data.id,
                        data.createdAt,
                        data.createdBy,
                        data.updatedAt,
                        data.updatedBy,
                        data.hashedToken,
                        data.calendars,
                        data.animeAccountLinks);
    }

    private UserData() {

    }
}
