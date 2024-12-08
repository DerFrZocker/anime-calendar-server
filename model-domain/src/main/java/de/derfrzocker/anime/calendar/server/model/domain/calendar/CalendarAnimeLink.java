package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.time.Instant;

public record CalendarAnimeLink(CalendarId calendarId, AnimeId animeId, Instant createdAt, UserId createdBy,
                                Instant updatedAt, UserId updatedBy, boolean include) implements ModificationInfo {

    public static CalendarAnimeLink from(CalendarId calendarId,
                                         AnimeId animeId,
                                         CalendarAnimeLinkCreateData createData,
                                         RequestContext context) {
        return new CalendarAnimeLink(calendarId,
                                     animeId,
                                     context.requestTime(),
                                     context.requestUser(),
                                     context.requestTime(),
                                     context.requestUser(),
                                     createData.include());
    }

    public CalendarAnimeLink updateWithData(CalendarAnimeLinkUpdateData updateData, RequestContext context) {
        return new CalendarAnimeLink(calendarId(),
                                     animeId(),
                                     createdAt(),
                                     createdBy(),
                                     context.requestTime(),
                                     context.requestUser(),
                                     updateData.include().apply(include()));
    }
}
