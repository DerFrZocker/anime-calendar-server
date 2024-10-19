package de.derfrzocker.anime.calendar.server.model.domain.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public record Anime(AnimeId animeId, @NotNull String animeName, int episodeCount,
                    @NotNull List<@NotNull LayerHolder> transformerData) {
}
