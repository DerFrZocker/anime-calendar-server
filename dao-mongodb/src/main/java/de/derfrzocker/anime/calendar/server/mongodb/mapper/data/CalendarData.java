package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarDO;

public final class CalendarData {

    public static Calendar toDomain(CalendarDO data) {
        return new Calendar(data.id,
                            data.key,
                            data.createdAt,
                            data.createdBy,
                            data.updatedAt,
                            data.updatedBy,
                            data.owner,
                            data.name);
    }

    private CalendarData() {

    }
}
