package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;

public record UserCreateData(UserId userId, HashedUserToken hashedUserToken) {

}
