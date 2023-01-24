package de.derfrzocker.anime.calendar.api;

import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CalendarService {

    @NotNull
    Calendar buildCalendar(@NotNull List<@NotNull Integer> animeIds, @NotNull AnimeOptions options);


    @NotNull
    List<Integer> getAnimeIds(@NotNull String idProvider, @NotNull String userId);

    void saveAnimeIds(@NotNull String idProvider, @NotNull String userId, @NotNull List<@NotNull Integer> animeIds);
}
