package de.derfrzocker.anime.calendar.server.model.core.calendar;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;

public record CalendarId(String raw) implements Id {

    public static final char ID_PREFIX = 'C';

    @Override
    public IdType idType() {
        return IdType.CALENDAR;
    }
}
