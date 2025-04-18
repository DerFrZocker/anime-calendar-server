package de.derfrzocker.anime.calendar.server.episode.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.Episode;
import java.util.List;

public interface EpisodeBuilderService {

    List<Episode> buildEpisodes(Anime anime, AnimeOptions animeOptions, RequestContext context);

    Episode buildEpisode(Anime anime, AnimeOptions animeOptions, int index, RequestContext context);
}
