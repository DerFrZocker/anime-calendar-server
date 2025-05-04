package de.derfrzocker.anime.calendar.core.exception;

import java.util.function.Supplier;

/**
 * Indicates that something unexpected happened.
 */
public class UnexpectedException extends RuntimeException {

    public static Supplier<UnexpectedException> from(String message) {
        return () -> new UnexpectedException(message);
    }

    private UnexpectedException(String message) {
        super(message);
    }
}
