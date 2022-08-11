package de.derfrzocker.anime.calendar.server;

import de.derfrzocker.anime.calendar.server.provider.AnimeStreamProvider;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;

import java.util.List;
import java.util.Map;

public record Anime(int animeId, String name, List<Episode> episodes, Map<Region, String> regionName, Map<AnimeUserInfoProvider, String> externId, Map<AnimeStreamProvider, String> streamId) {
}
