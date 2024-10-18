package de.derfrzocker.anime.calendar.api;

import de.derfrzocker.anime.calendar.server.model.domain.AnimeEpisodes;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICalCalendarBuilder {

    @NotNull
    Calendar buildCalendar(@NotNull List<@NotNull AnimeEpisodes> animeEpisodes);
}
