package de.derfrzocker.anime.calendar.exception;

import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public class AnimeCalendarException extends RuntimeException {

    @Nullable
    private final String publicMessage;
    private final String errorCode;

    public AnimeCalendarException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.publicMessage = null;
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public AnimeCalendarException(String publicMessage, String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.publicMessage = Objects.requireNonNull(publicMessage);
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public AnimeCalendarException(String publicMessage, String message, String errorCode) {
        super(message);
        this.publicMessage = Objects.requireNonNull(publicMessage);
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public Optional<String> publicMessage() {
        return Optional.ofNullable(publicMessage);
    }

    public String errorCode() {
        return errorCode;
    }
}
