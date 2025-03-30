package de.derfrzocker.anime.calendar.core.calendar;

public record CalendarKey(String raw) {

    public static final int KEY_LENGTH = CalendarId.ID_LENGTH + 30;
    public static final char KEY_PREFIX_CHAR = 'K';

    public CalendarId calendarId() {
        return new CalendarId(raw().substring(0, CalendarId.ID_LENGTH));
    }
}
