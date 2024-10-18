package de.derfrzocker.anime.calendar.server.model.core;

public record CalendarId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.CALENDAR;
    }
}
