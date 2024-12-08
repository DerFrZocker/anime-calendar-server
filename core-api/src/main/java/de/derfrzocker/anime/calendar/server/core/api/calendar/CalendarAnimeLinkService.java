package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface CalendarAnimeLinkService {

    Stream<CalendarAnimeLink> getAllWithId(CalendarId calendarId, RequestContext context);

    Optional<CalendarAnimeLink> getById(CalendarId calendarId, AnimeId animeId, RequestContext context);

    CalendarAnimeLink createWithData(CalendarId calendarId,
                                     AnimeId animeId,
                                     CalendarAnimeLinkCreateData createData,
                                     RequestContext context);

    CalendarAnimeLink updateWithData(CalendarId calendarId,
                                     AnimeId animeId,
                                     CalendarAnimeLinkUpdateData updateData,
                                     RequestContext context);

    void deleteById(CalendarId calendarId, AnimeId animeId, RequestContext context);
}
