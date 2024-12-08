package de.derfrzocker.anime.calendar.server.model.domain.exception;

import java.util.function.Supplier;

public class BadRequestException extends RuntimeException {

    public static Supplier<BadRequestException> with(String message, Object... args) {
        return () -> new BadRequestException(message.formatted(args));
    }

    private BadRequestException(String message) {
        super(message);
    }
}
