package de.derfrzocker.anime.calendar.api;

import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CalendarService {

    @NotNull
    Calendar buildCalendar(@NotNull List<@NotNull String> animeIds, @NotNull AnimeOptions options);
}
