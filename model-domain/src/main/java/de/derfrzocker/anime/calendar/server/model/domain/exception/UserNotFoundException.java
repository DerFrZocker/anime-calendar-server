package de.derfrzocker.anime.calendar.server.model.domain.exception;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.util.function.Supplier;

public class UserNotFoundException extends ResourceNotFoundException {

    private static final String USER_NOT_FOUND = "The requested user with the id '%s' could not be found.";

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static Supplier<UserNotFoundException> withId(UserId id) {
        return () -> new UserNotFoundException(USER_NOT_FOUND.formatted(id.raw()));
    }
}
