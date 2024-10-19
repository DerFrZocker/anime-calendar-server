package de.derfrzocker.anime.calendar.server.model.core.calendar;

public record CalendarKey(String raw) {

    public static final int CALENDAR_KEY_LENGTH = CalendarId.ID_LENGTH + 20;
    public static final char KEY_PREFIX_CHAR = 'K';

    CalendarId calendarId() {
        return new CalendarId(raw().substring(0, CalendarId.ID_LENGTH));
    }
}
