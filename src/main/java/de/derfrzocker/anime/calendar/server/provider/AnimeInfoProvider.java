package de.derfrzocker.anime.calendar.server.provider;

import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.Episode;
import de.derfrzocker.anime.calendar.server.RegionStream;

import java.net.URI;

public interface AnimeInfoProvider {

    Anime getAnime(AnimeUserInfoProvider provider, String externId);
}
