package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;
import java.util.Set;

public record User(UserId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy,
                   HashedUserToken hashedToken, Set<CalendarId> calendars,
                   Set<AnimeAccountLinkId> animeAccountLinks) implements ModificationInfo {

    public static User from(UserId id, HashedUserToken hashedToken, RequestContext context) {
        return new User(id,
                        context.requestTime(),
                        context.requestUser(),
                        context.requestTime(),
                        context.requestUser(),
                        hashedToken,
                        Set.of(),
                        Set.of());
    }

    public User updateWithData(UserUpdateData updateData, RequestContext context) {
        return new User(id(),
                        createdAt(),
                        createdBy(),
                        context.requestTime(),
                        context.requestUser(),
                        hashedToken(),
                        updateData.calendars().apply(calendars()),
                        animeAccountLinks());
    }
}
