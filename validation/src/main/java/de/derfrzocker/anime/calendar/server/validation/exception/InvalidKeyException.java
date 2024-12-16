package de.derfrzocker.anime.calendar.server.validation.exception;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;

public class InvalidKeyException extends RuntimeException {

    private static final String INVALID_KEY = " '%s' is not valid.";
    private static final String CALENDAR_KEY = "CalendarKey" + INVALID_KEY;

    public static InvalidKeyException with(CalendarKey key) {
        if (key == null) {
            return with("<null>", CALENDAR_KEY);
        }

        return with(key.raw(), CALENDAR_KEY);
    }

    private static InvalidKeyException with(String id, String message) {
        return new InvalidKeyException(message.formatted(id));
    }

    private InvalidKeyException(String message) {
        super(message);
    }
}
