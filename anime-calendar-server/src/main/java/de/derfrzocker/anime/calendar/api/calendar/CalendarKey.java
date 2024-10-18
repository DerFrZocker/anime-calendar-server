package de.derfrzocker.anime.calendar.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.CalendarId;

public record CalendarKey(String key) {

    public static final int CALENDAR_KEY_LENGTH = CalendarId.ID_LENGTH + 20;
    public static final char KEY_PREFIX_CHAR = 'K';

    CalendarId calendarId() {
        return new CalendarId(key.substring(0, CalendarId.ID_LENGTH));
    }
}
