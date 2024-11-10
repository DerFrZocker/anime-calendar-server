package de.derfrzocker.anime.calendar.server.model.domain.event.user;

import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;

public record PreUserCreateEvent(UserCreateData userCreateData) {

}
