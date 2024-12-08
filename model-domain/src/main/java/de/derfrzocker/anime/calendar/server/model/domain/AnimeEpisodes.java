package de.derfrzocker.anime.calendar.server.model.domain;

import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import java.util.List;

public record AnimeEpisodes(Anime anime, List<Episode> episodes) {

}
