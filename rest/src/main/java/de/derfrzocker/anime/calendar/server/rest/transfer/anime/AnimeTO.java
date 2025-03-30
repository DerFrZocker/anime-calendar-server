package de.derfrzocker.anime.calendar.server.rest.transfer.anime;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;

public record AnimeTO(AnimeId id, String title, int episodeCount) {

}
