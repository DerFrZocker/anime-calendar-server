package de.derfrzocker.anime.calendar.server.model.domain.event.user;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;

public record PreUserCreateEvent(User user, RequestContext context) {

}
