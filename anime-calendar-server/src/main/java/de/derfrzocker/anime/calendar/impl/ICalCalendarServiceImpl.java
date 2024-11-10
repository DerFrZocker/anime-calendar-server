package de.derfrzocker.anime.calendar.impl;

import de.derfrzocker.anime.calendar.server.core.api.ICalCalendarBuilder;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.core.api.calendar.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.core.api.layer.LayerService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.Episode;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
public class ICalCalendarServiceImpl implements ICalCalendarService {

    @Inject
    @NotNull
    AnimeService animeService;
    @Inject
    @NotNull
    LayerService transformerService;
    @Inject
    @NotNull
    ICalCalendarBuilder calendarBuilder;

    @Override
    public Calendar buildCalendar(Set<AnimeId> animeIds, AnimeOptions options) {
        List<AnimeEpisodes> animeEpisodes = new ArrayList<>();
        for (AnimeId animeId : animeIds) {
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
