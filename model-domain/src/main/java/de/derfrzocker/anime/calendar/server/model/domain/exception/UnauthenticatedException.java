package de.derfrzocker.anime.calendar.server.model.domain.exception;

/**
 * Indicates that no valid user is logged in
 */
public class UnauthenticatedException extends Exception {

    private static final String ERROR_MESSAGE = "Unauthenticated - Please add your user token.";

    public UnauthenticatedException() {
        this(ERROR_MESSAGE);
    }

    public UnauthenticatedException(Throwable cause) {
        this(ERROR_MESSAGE, cause);
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
