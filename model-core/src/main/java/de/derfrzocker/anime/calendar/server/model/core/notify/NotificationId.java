package de.derfrzocker.anime.calendar.server.model.core.notify;

public record NotificationId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'N';
}
