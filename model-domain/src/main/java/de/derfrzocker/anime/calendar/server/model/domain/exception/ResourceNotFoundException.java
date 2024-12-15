package de.derfrzocker.anime.calendar.server.model.domain.exception;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Indicates that the requested value could not be found
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final String NOT_FOUND = " with id '%s' not found.";
    private static final String ANIME_ID = "Anime" + NOT_FOUND;
    private static final String USER_ID = "User" + NOT_FOUND;
    private static final String CALENDAR_ID = "Calendar" + NOT_FOUND;
    private static final String USER_PERMISSION = "UserPermission" + NOT_FOUND;
    private static final String CALENDAR_ANIME_ID = "Calendar Anime link for calendar with id '%s' and anime with id " + "'%s' not found.";

    public static Supplier<ResourceNotFoundException> with(AnimeId id) {
        return with(unwrapSafe(id, AnimeId::raw), ANIME_ID);
    }

    public static Supplier<ResourceNotFoundException> with(UserId id) {
        return with(unwrapSafe(id, UserId::raw), USER_ID);
    }

    public static Supplier<ResourceNotFoundException> with(CalendarId id) {
        return with(unwrapSafe(id, CalendarId::raw), CALENDAR_ID);
    }

    public static Supplier<ResourceNotFoundException> with(CalendarId calendarId, AnimeId animeId) {
        return () -> new ResourceNotFoundException(CALENDAR_ANIME_ID.formatted(unwrapSafe(calendarId, CalendarId::raw),
                                                                               unwrapSafe(animeId, AnimeId::raw)));
    }

    public static Supplier<ResourceNotFoundException> withPermission(UserId id) {
        return () -> new ResourceNotFoundException(String.format(USER_PERMISSION, id));
    }

    private static Supplier<ResourceNotFoundException> with(String id, String message) {
        return () -> new ResourceNotFoundException(message.formatted(id));
    }

    private static <T> String unwrapSafe(T value, Function<T, Object> function) {
        if (value == null) {
            return "<null>";
        }

        return String.valueOf(function.apply(value));
    }

    private ResourceNotFoundException(String message) {
        super(message);
    }
}
