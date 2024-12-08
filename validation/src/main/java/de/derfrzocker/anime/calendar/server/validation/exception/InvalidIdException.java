package de.derfrzocker.anime.calendar.server.validation.exception;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;

public class InvalidIdException extends RuntimeException {

    private static final String INVALID_ID = " '%s' is not valid.";
    private static final String ANIME_ID = "AnimeId" + INVALID_ID;
    private static final String USER_ID = "UserId" + INVALID_ID;
    private static final String CALENDAR_ID = "CalendarId" + INVALID_ID;

    public static InvalidIdException with(AnimeId id) {
        if (id == null) {
            return with("<null>", ANIME_ID);
        }

        return with(id.raw(), ANIME_ID);
    }

    public static InvalidIdException with(UserId id) {
        if (id == null) {
            return with("<null>", ANIME_ID);
        }

        return with(id.raw(), USER_ID);
    }

    public static InvalidIdException with(CalendarId id) {
        if (id == null) {
            return with("<null>", ANIME_ID);
        }

        return with(id.raw(), CALENDAR_ID);
    }

    private static InvalidIdException with(String id, String message) {
        return new InvalidIdException(message.formatted(id));
    }

    private InvalidIdException(String message) {
        super(message);
    }
}
