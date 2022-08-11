package de.derfrzocker.anime.calendar.server;

import de.derfrzocker.anime.calendar.server.provider.AnimeStreamProvider;

import java.time.LocalDateTime;

public record RegionStream(Region region, AnimeStreamProvider streamProvider, LocalDateTime releaseDate) {
}
