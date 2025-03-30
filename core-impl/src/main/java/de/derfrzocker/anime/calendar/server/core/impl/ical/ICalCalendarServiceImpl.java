package de.derfrzocker.anime.calendar.server.core.impl.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarBuilder;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.core.api.layer.LayerService;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.Episode;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dependent
public class ICalCalendarServiceImpl implements ICalCalendarService {

    @Inject
    AnimeService animeService;
    @Inject
    LayerService layerService;
    @Inject
    ICalCalendarBuilder calendarBuilder;

    @Override
    public ICalCalendar build(Set<AnimeId> ids, AnimeOptions options, RequestContext context) {
        List<AnimeEpisodes> animeEpisodes = new ArrayList<>();
        for (AnimeId id : ids) {
            Optional<Anime> anime = this.animeService.getById(id, context);

            if (anime.isEmpty()) {
                // TODO 2024-12-15: Log message
                continue;
            }

            List<Episode> episodes = this.layerService.transformAnime(anime.get(), options);

            if (episodes.isEmpty()) {
                continue;
            }

            animeEpisodes.add(new AnimeEpisodes(anime.get(), episodes));
        }

        return this.calendarBuilder.build(animeEpisodes, context);
    }
}
