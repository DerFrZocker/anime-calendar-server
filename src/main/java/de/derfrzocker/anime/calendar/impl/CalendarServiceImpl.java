package de.derfrzocker.anime.calendar.impl;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.AnimeService;
import de.derfrzocker.anime.calendar.api.CalendarBuilder;
import de.derfrzocker.anime.calendar.api.CalendarService;
import de.derfrzocker.anime.calendar.api.Episode;
import de.derfrzocker.anime.calendar.api.layer.LayerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CalendarServiceImpl implements CalendarService {

    @Inject
    @NotNull
    AnimeService animeService;
    @Inject
    @NotNull
    LayerService transformerService;
    @Inject
    @NotNull
    CalendarBuilder calendarBuilder;

    @Override
    public @NotNull Calendar buildCalendar(@NotNull List<@NotNull String> animeIds, @NotNull AnimeOptions options) {
        List<AnimeEpisodes> animeEpisodes = new ArrayList<>();
        for (String animeId : animeIds) {
            Anime anime = animeService.getAnime(animeId);

            if (anime == null) {
                continue;
            }

            List<Episode> episodes = transformerService.transformAnime(anime, options);

            if (episodes.isEmpty()) {
                continue;
            }

            animeEpisodes.add(new AnimeEpisodes(anime, episodes));
        }

        return calendarBuilder.buildCalendar(animeEpisodes);
    }

}
