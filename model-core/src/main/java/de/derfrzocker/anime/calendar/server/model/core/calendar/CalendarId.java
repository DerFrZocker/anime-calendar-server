package de.derfrzocker.anime.calendar.server.model.core.calendar;

public record CalendarId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'C';
}
