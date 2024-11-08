package de.derfrzocker.anime.calendar.server.model.domain.exception;

/**
 * Indicates that the requested value could not be found
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
