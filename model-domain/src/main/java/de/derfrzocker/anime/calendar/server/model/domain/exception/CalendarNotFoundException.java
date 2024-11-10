package de.derfrzocker.anime.calendar.server.model.domain.exception;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import java.util.function.Supplier;

public class CalendarNotFoundException extends ResourceNotFoundException {

    private static final String CALENDAR_NOT_FOUND = "The requested calendar with the id '%s' could not be found.";

    public CalendarNotFoundException(String message) {
        super(message);
    }

    public CalendarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static Supplier<CalendarNotFoundException> withId(CalendarId id) {
        return () -> new CalendarNotFoundException(CALENDAR_NOT_FOUND.formatted(id.raw()));
    }
}
