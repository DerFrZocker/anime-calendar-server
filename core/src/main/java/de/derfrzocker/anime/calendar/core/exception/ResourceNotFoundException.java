package de.derfrzocker.anime.calendar.core.exception;

import de.derfrzocker.anime.calendar.core.util.WrapperUtil;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Indicates that the requested value could not be found.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final String NOT_FOUND = "%s with id '%s' not found.";

    public static Supplier<ResourceNotFoundException> from(String message) {
        return () -> new ResourceNotFoundException(message);
    }

    public static <T> Supplier<ResourceNotFoundException> with(@Nullable T id, String resourceName) {
        return () -> new ResourceNotFoundException(NOT_FOUND.formatted(resourceName, WrapperUtil.toString(id)));
    }

    private ResourceNotFoundException(String message) {
        super(message);
    }
}
