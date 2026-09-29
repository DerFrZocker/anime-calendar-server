package de.derfrzocker.anime.calendar.external.myanimylist.exception;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class AnimeCalendarException extends RuntimeException {

    @Nullable
    private final String publicMessage;
    private final String errorCode;

    public AnimeCalendarException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.publicMessage = null;
        this.errorCode = errorCode;
    }

    public AnimeCalendarException(String publicMessage, String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.publicMessage = Objects.requireNonNull(publicMessage);
        this.errorCode = errorCode;
    }

    public AnimeCalendarException(String publicMessage, String message, String errorCode) {
        super(message);
        this.publicMessage = Objects.requireNonNull(publicMessage);
        this.errorCode = errorCode;
    }
}
