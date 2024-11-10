package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;

public interface ICalCalendarService {

    Calendar buildCalendar(Set<AnimeId> animeIds, AnimeOptions options);
}
