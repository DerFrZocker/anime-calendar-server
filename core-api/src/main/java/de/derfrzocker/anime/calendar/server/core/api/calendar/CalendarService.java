package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

public interface CalendarService {

    @NotNull
    Calendar buildCalendar(@NotNull Set<@NotNull AnimeId> animeIds, @NotNull AnimeOptions options);
}
