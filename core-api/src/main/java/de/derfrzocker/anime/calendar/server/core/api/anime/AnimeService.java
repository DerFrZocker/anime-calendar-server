package de.derfrzocker.anime.calendar.server.core.api.anime;

import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public interface AnimeService {

    @Nullable
    Anime getAnime(AnimeId id);

    boolean isAnime(AnimeId id);

    Anime createAnime(String animeTitle, int episodeCount, List<Map<String, Object>> episodeLayers);
}
