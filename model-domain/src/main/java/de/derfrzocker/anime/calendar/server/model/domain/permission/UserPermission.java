package de.derfrzocker.anime.calendar.server.model.domain.permission;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.time.Instant;
import java.util.HashMap;

public record UserPermission(UserId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy,
                             ObjectPermission<UserId> user, ObjectPermission<CalendarId> calendar,
                             ObjectPermission<AnimeId> anime,
                             ObjectPermission<CalendarId> calendarAnimeLink) implements ModificationInfo {

    public static UserPermission from(UserId id, UserPermissionCreateData createData, RequestContext context) {
        return new UserPermission(id,
                                  context.requestTime(),
                                  context.requestUser(),
                                  context.requestTime(),
                                  context.requestUser(),
                                  new ObjectPermission<>(new HashMap<>(), null),
                                  new ObjectPermission<>(new HashMap<>(), null),
                                  new ObjectPermission<>(new HashMap<>(), null),
                                  new ObjectPermission<>(new HashMap<>(), null));
    }

    public UserPermission updateWithData(UserPermissionUpdateData updateData, RequestContext context) {
        return new UserPermission(id(),
                                  createdAt(),
                                  createdBy(),
                                  context.requestTime(),
                                  context.requestUser(),
                                  updateData.user().apply(user()),
                                  updateData.calendar().apply(calendar()),
                                  updateData.anime().apply(anime()),
                                  updateData.calendarAnimeLink().apply(calendarAnimeLink()));
    }
}
