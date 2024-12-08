package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.rest.transfer.user.UserTO;

public final class UserDomain {

    public static UserTO toTransfer(User domain) {
        return new UserTO(domain.id());
    }

    private UserDomain() {

    }
}
