package de.derfrzocker.anime.calendar.impl;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.CalendarBuilder;
import de.derfrzocker.anime.calendar.api.CalendarService;
import de.derfrzocker.anime.calendar.api.Episode;
import de.derfrzocker.anime.calendar.api.dao.AnimeDao;
import de.derfrzocker.anime.calendar.api.transformer.TransformerService;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CalendarServiceImpl implements CalendarService {

    @NotNull
    private final AnimeDao animeDao;
    @NotNull
    private final TransformerService transformerService;
    @NotNull
    private final CalendarBuilder calendarBuilder;

    public CalendarServiceImpl(@NotNull AnimeDao animeDao, @NotNull TransformerService transformerService, @NotNull CalendarBuilder calendarBuilder) {
        this.animeDao = animeDao;
        this.transformerService = transformerService;
        this.calendarBuilder = calendarBuilder;
    }

    @Override
    public @NotNull Calendar buildCalendar(@NotNull List<@NotNull Integer> animeIds, @NotNull AnimeOptions options) {
        List<AnimeEpisodes> animeEpisodes = new ArrayList<>();
        for (int animeId : animeIds) {
            Anime anime = animeDao.getAnime(animeId);

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

    @Override
    public @NotNull List<@NotNull Integer> getAnimeIds(@NotNull String idProvider, @NotNull String userId) {
        if (!"anime-calendar".equals(idProvider)) {
            throw new IllegalArgumentException("Only anime-calendar is allowed currently");
        }
        return animeDao.getAnimeIds(userId);
    }

    @Override
    public void saveAnimeIds(@NotNull String idProvider, @NotNull String userId, @NotNull List<@NotNull Integer> animeIds) {
        if (!"anime-calendar".equals(idProvider)) {
            throw new IllegalArgumentException("Only anime-calendar is allowed currently");
        }

        animeDao.saveAnimeIds(userId, animeIds);
    }
}
