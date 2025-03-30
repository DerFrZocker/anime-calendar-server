package de.derfrzocker.anime.calendar.core.exception;

import java.util.function.Supplier;

/**
 * Indicates that the requested value could not be found, but it was expected to be there.
 */
public class InconsistentDataException extends RuntimeException {

    private static final String NOT_FOUND = "%s with id '%s' not found, but it should be present.";

    public static Supplier<InconsistentDataException> from(String message) {
        return () -> new InconsistentDataException(message);
    }

    public static <T> Supplier<InconsistentDataException> with(T id, String resourceName) {
        return () -> new InconsistentDataException(NOT_FOUND.formatted(resourceName, toString(id)));
    }

    private static <T> String toString(T id) {
        if (id == null) {
            return "<null>";
        }

        return String.valueOf(id);
    }

    private InconsistentDataException(String message) {
        super(message);
    }
}
