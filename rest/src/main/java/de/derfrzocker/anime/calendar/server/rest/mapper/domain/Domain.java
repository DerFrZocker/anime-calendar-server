package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.rest.transfer.user.UserTO;

public final class Domain {

    private Domain() {
    }

    public static UserTO toTransfer(User domain) {
        return UserDomainMapper.toTransfer(domain);
    }
}
