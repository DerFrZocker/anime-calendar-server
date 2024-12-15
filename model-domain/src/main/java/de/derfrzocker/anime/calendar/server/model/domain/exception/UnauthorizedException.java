package de.derfrzocker.anime.calendar.server.model.domain.exception;

/**
 * Indicates that no valid user is logged in
 */
public class UnauthorizedException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unauthorized - You are not allowed to perform this action.";

    public UnauthorizedException() {
        super(ERROR_MESSAGE);
    }
}
