package de.derfrzocker.anime.calendar.core.exception;

import java.util.function.Supplier;

/**
 * Indicates that the user is not allowed to performe this action.
 */
public class ActionNotAllowedException extends RuntimeException {

    public static Supplier<ActionNotAllowedException> from(String message) {
        return () -> new ActionNotAllowedException(message);
    }

    private ActionNotAllowedException(String message) {
        super(message);
    }
}
