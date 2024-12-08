package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarAnimeLinkDO;

public final class CalendarAnimeLinkData {

    public static CalendarAnimeLink toDomain(CalendarAnimeLinkDO data) {
        return new CalendarAnimeLink(data.calendarId,
                                     data.animeId,
                                     data.createdAt,
                                     data.createdBy,
                                     data.updatedAt,
                                     data.updatedBy,
                                     data.include);
    }

    private CalendarAnimeLinkData() {

    }
}
