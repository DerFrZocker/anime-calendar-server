package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record Calendar(CalendarId id, CalendarKey key, Instant createdAt, UserId createdBy, Instant updatedAt,
                       UserId updatedBy, UserId owner, String name) implements ModificationInfo {

    public static Calendar from(CalendarId id, CalendarKey key, CalendarCreateData createData, RequestContext context) {
        return new Calendar(id,
                            key,
                            context.requestTime(),
                            context.requestUser(),
                            context.requestTime(),
                            context.requestUser(),
                            createData.owner(),
                            createData.name());
    }
}
