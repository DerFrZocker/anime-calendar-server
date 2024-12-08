package de.derfrzocker.anime.calendar.server.model.domain.event.user;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserUpdateData;

public record PreUserUpdateEvent(UserId id, UserUpdateData updateData, User current, User updated,
                                 RequestContext context) {

}
