package de.derfrzocker.anime.calendar.server.model.core;

public enum IdType {

    ANIME('A'),
    USER('U'),
    CALENDAR('C'),
    ANIME_ACCOUNT_LINK('L');

    private final char prefix;

    IdType(char prefix) {
        this.prefix = prefix;
    }

    public char prefix() {
        return prefix;
    }
}
