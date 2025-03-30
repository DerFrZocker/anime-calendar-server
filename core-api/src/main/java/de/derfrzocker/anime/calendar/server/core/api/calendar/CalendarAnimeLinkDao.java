package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import java.util.Optional;
import java.util.stream.Stream;

public interface CalendarAnimeLinkDao {

    Stream<CalendarAnimeLink> getAllWithId(CalendarId calendarId, RequestContext context);

    Optional<CalendarAnimeLink> getById(CalendarId calendarId, AnimeId animeId, RequestContext context);

    void create(CalendarAnimeLink calendarAnimeLink, RequestContext context);

    void update(CalendarAnimeLink calendarAnimeLink, RequestContext context);

    void delete(CalendarAnimeLink calendarAnimeLink, RequestContext context);
}
