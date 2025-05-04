package de.derfrzocker.anime.calendar.core.exception;

import java.util.function.Supplier;

/**
 * Indicates that no authentication was provided.
 */
public class UnauthorizedException extends RuntimeException {

    public static Supplier<UnauthorizedException> from(String message) {
        return () -> new UnauthorizedException(message);
    }

    private UnauthorizedException(String message) {
        super(message);
    }
}
