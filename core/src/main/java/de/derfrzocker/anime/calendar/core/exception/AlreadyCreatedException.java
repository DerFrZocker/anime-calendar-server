package de.derfrzocker.anime.calendar.core.exception;

import de.derfrzocker.anime.calendar.core.util.WrapperUtil;
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
        return () -> new AlreadyCreatedException(ALREADY_CREATED.formatted(resourceName, WrapperUtil.toString(id)));
    }

    private AlreadyCreatedException(String message) {
        super(message);
    }
}
