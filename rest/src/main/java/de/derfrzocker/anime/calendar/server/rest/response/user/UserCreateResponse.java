package de.derfrzocker.anime.calendar.server.rest.response.user;

import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import de.derfrzocker.anime.calendar.server.rest.transfer.user.UserTO;

public record UserCreateResponse(UserToken apiToken, UserTO user) {

}
