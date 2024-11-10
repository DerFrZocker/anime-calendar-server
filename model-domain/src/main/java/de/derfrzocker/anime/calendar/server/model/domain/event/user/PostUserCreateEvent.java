package de.derfrzocker.anime.calendar.server.model.domain.event.user;

import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;

public record PostUserCreateEvent(User user, UserCreateData userCreateData) {

}
