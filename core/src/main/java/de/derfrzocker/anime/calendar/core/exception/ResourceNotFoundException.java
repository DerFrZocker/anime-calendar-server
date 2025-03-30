package de.derfrzocker.anime.calendar.core.exception;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Indicates that the requested value could not be found.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final String NOT_FOUND = "%s with id '%s' not found.";
    private static final String NOT_FOUND_OLD = " with id '%s' not found.";
    private static final String ANIME_ID = "Anime" + NOT_FOUND_OLD;
    private static final String USER_ID = "User" + NOT_FOUND_OLD;
    private static final String CALENDAR_ID = "Calendar" + NOT_FOUND_OLD;
    private static final String USER_PERMISSION = "UserPermission" + NOT_FOUND_OLD;
    private static final String CALENDAR_ANIME_ID = "Calendar Anime link for calendar with id '%s' and anime with id '%s' not found.";
    private static final String ANIME_NAME = "Anime name for integration '%s' and integration anime id '%s' not found.";
    private static final String SEASON_INFO = "Anime season info for integration '%s', integration anime id '%s', year '%d' and season '%s' not found.";

    public static Supplier<ResourceNotFoundException> from(String message) {
        return () -> new ResourceNotFoundException(message);
    }

    public static Supplier<ResourceNotFoundException> with(AnimeId id) {
        return with(unwrapSafe(id, AnimeId::raw), ANIME_ID);
    }

    public static Supplier<ResourceNotFoundException> with(UserId id) {
        return with(unwrapSafe(id, UserId::raw), USER_ID);
    }

    public static Supplier<ResourceNotFoundException> with(CalendarId id) {
        return with(unwrapSafe(id, CalendarId::raw), CALENDAR_ID);
    }

    public static <T> Supplier<ResourceNotFoundException> with(T id, String resourceName) {
        return () -> new ResourceNotFoundException(NOT_FOUND.formatted(resourceName, toString(id)));
    }

    public static Supplier<ResourceNotFoundException> with(CalendarId calendarId, AnimeId animeId) {
        return () -> new ResourceNotFoundException(CALENDAR_ANIME_ID.formatted(unwrapSafe(calendarId, CalendarId::raw),
                                                                               unwrapSafe(animeId, AnimeId::raw)));
    }

    public static Supplier<ResourceNotFoundException> withPermission(UserId id) {
        return () -> new ResourceNotFoundException(USER_PERMISSION.formatted(unwrapSafe(id, UserId::raw)));
    }

    public static Supplier<ResourceNotFoundException> withAnimeName(IntegrationId integrationId,
                                                                    IntegrationAnimeId integrationAnimeId) {
        return () -> new ResourceNotFoundException(ANIME_NAME.formatted(unwrapSafe(integrationId, IntegrationId::raw),
                                                                        unwrapSafe(integrationAnimeId,
                                                                                   IntegrationAnimeId::raw)));
    }

    public static Supplier<ResourceNotFoundException> withSeasonInfo(IntegrationId integrationId,
                                                                     IntegrationAnimeId integrationAnimeId,
                                                                     int year,
                                                                     Season season) {
        return () -> new ResourceNotFoundException(SEASON_INFO.formatted(unwrapSafe(integrationId, IntegrationId::raw),
                                                                         unwrapSafe(integrationAnimeId,
                                                                                    IntegrationAnimeId::raw),
                                                                         year,
                                                                         season));
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

    private static <T> String toString(T id) {
        if (id == null) {
            return "<null>";
        }

        return String.valueOf(id);
    }

    private ResourceNotFoundException(String message) {
        super(message);
    }
}
