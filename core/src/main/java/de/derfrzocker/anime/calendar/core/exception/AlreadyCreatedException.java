package de.derfrzocker.anime.calendar.core.exception;

import java.util.function.Supplier;

/**
 * Indicates that a resource which should be created is already created with a given id.
 */
public class AlreadyCreatedException extends RuntimeException {

    private static final String ALREADY_CREATED = "%s with id '%s' was already created and cannot be created again.";

    public static Supplier<AlreadyCreatedException> from(String message) {
        return () -> new AlreadyCreatedException(message);
    }

    public static <T> Supplier<AlreadyCreatedException> with(T id, String resourceName) {
        return () -> new AlreadyCreatedException(ALREADY_CREATED.formatted(resourceName, toString(id)));
    }

    private static <T> String toString(T id) {
        if (id == null) {
            return "<null>";
        }

        return String.valueOf(id);
    }

    private AlreadyCreatedException(String message) {
        super(message);
    }
}
