package de.derfrzocker.anime.calendar.episode.service;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.episode.api.Episode;
import java.util.List;

public interface EpisodeBuilderService {

    List<Episode> buildEpisodes(Anime anime, AnimeOptions animeOptions, RequestContext context);

    Episode buildEpisode(Anime anime, AnimeOptions animeOptions, int index, RequestContext context);
}
