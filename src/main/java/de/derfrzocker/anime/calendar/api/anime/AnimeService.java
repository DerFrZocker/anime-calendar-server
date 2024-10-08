package de.derfrzocker.anime.calendar.api.anime;

import de.derfrzocker.anime.calendar.web.request.anime.AnimePostRequest;
import org.jetbrains.annotations.Nullable;

public interface AnimeService {

    @Nullable
    Anime getAnime(AnimeId id);

    boolean isAnime(AnimeId id);

    Anime createAnime(AnimePostRequest animePostRequest);
}
