package de.derfrzocker.anime.calendar.server.provider;

import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.Region;

import java.net.URI;

public interface AnimeStreamProvider {

    String getProviderName();

    URI createAnimeStreamLink(Anime anime, Region region);
}
