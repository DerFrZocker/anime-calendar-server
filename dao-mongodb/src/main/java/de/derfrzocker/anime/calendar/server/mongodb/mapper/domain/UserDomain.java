package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.mongodb.data.user.UserDO;

public final class UserDomain {

    public static UserDO toData(User domain) {
        UserDO data = new UserDO();

        data.id = domain.id();
        data.hashedToken = domain.hashedToken();
        data.calendars = domain.calendars();
        data.animeAccountLinks = domain.animeAccountLinks();
        data.apply(domain);

        return data;
    }

    private UserDomain() {

    }
}
