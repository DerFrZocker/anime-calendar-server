package de.derfrzocker.anime.calendar.core.exception;

import de.derfrzocker.anime.calendar.core.util.WrapperUtil;
import java.util.function.Supplier;

/**
 * Indicates that the requested value could not be found, but it was expected to be there.
 */
public class InconsistentDataException extends RuntimeException {

    private static final String NOT_FOUND = "%s with id '%s' not found, but it should be present.";

    public static Supplier<InconsistentDataException> from(String message, Throwable cause) {
        return () -> new InconsistentDataException(message, cause);
    }

    public static Supplier<InconsistentDataException> from(String message) {
        return () -> new InconsistentDataException(message);
    }

    public static <T> Supplier<InconsistentDataException> with(T id, String resourceName) {
        return () -> new InconsistentDataException(NOT_FOUND.formatted(resourceName, WrapperUtil.toString(id)));
    }

    private InconsistentDataException(String message, Throwable cause) {
        super(message, cause);
    }

    private InconsistentDataException(String message) {
        super(message);
    }
}
