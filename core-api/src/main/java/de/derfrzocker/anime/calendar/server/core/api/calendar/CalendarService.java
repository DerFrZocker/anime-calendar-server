package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverrideCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.exception.CalendarNotFoundException;
import java.util.Optional;

public interface CalendarService {

    Optional<Calendar> getById(CalendarId id);

    Calendar createWithUser(UserId userId);

    Calendar createAnimeOverride(CalendarId id, AnimeOverrideCreateData animeOverrideCreateData) throws
                                                                                                 CalendarNotFoundException;
}
