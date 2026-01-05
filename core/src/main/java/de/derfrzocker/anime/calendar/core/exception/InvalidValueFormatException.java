package de.derfrzocker.anime.calendar.core.exception;

import org.jetbrains.annotations.NotNull;

public class InvalidValueFormatException
        extends RuntimeException {

    public InvalidValueFormatException(@NotNull String message) {
        super(message);
    }
}
