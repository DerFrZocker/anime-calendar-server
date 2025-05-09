package de.derfrzocker.anime.calendar.server.ical.impl;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.ical.ICalCalendarBuilder;
import de.derfrzocker.anime.calendar.server.ical.ICalCalendarConverter;
import de.derfrzocker.anime.calendar.server.ical.api.ICalCalendar;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class ICalCalendarBuilderImpl implements ICalCalendarBuilder {

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;
    @Inject
    ICalCalendarConverter iCalCalendarConverter;

    @Override
    public ICalCalendar build(Collection<AnimeId> ids, AnimeOptions options, RequestContext context) {
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

        return this.iCalCalendarConverter.convert(animeEpisodes, context);
    }
}
