package de.derfrzocker.anime.calendar.server.core.api;

import de.derfrzocker.anime.calendar.server.model.domain.AnimeEpisodes;
import java.util.List;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

public interface ICalCalendarBuilder {

    @NotNull
    Calendar buildCalendar(@NotNull List<@NotNull AnimeEpisodes> animeEpisodes);
}
