package de.derfrzocker.anime.calendar.server.episode.api;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import java.util.List;

public record AnimeEpisodes(Anime anime, List<Episode> episodes) {

}
