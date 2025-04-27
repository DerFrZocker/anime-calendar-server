package de.derfrzocker.anime.calendar.server.core.impl.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarBuilder;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Dependent
public class ICalCalendarServiceImpl implements ICalCalendarService {

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;
    @Inject
    ICalCalendarBuilder calendarBuilder;

    @Override
    public ICalCalendar build(Set<AnimeId> ids, AnimeOptions options, RequestContext context) {
        List<AnimeEpisodes> animeEpisodes = new ArrayList<>();

        try (Stream<Anime> animes = this.animeService.getAllByIds(ids, context)) {
            animes.forEach(anime -> {
                List<Episode> episodes = this.episodeBuilderService.buildEpisodes(anime, options, context);

                if (episodes.isEmpty()) {
                    return;
                }

                animeEpisodes.add(new AnimeEpisodes(anime, episodes));
            });
        }

        return this.calendarBuilder.build(animeEpisodes, context);
    }
}
