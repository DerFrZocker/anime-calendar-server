package de.derfrzocker.anime.calendar.server.model.core.user;

public record UserId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'U';
}
