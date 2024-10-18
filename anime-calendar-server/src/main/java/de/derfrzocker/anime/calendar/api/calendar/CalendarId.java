package de.derfrzocker.anime.calendar.api.calendar;

import de.derfrzocker.anime.calendar.api.Id;
import de.derfrzocker.anime.calendar.api.IdType;

public record CalendarId(String id) implements Id {

    @Override
    public IdType idType() {
        return IdType.CALENDAR;
    }
}
