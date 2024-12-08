package de.derfrzocker.anime.calendar.server.model.domain.exception;

/**
 * Indicates that no valid user is logged in
 */
public class UnauthenticatedException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unauthenticated - Please add your user token.";

    public UnauthenticatedException() {
        super(ERROR_MESSAGE);
    }
}
