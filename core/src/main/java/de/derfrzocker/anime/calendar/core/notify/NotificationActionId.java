package de.derfrzocker.anime.calendar.core.notify;

public record NotificationActionId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'O';
}
