package de.derfrzocker.anime.calendar.episode.api;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import java.util.List;

public record AnimeEpisodes(Anime anime, List<Episode> episodes) {

}
