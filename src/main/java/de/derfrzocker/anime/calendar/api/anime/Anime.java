package de.derfrzocker.anime.calendar.api.anime;

import de.derfrzocker.anime.calendar.api.layer.LayerHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Anime(AnimeId animeId, @NotNull String animeName, int episodeCount,
                    @NotNull List<@NotNull LayerHolder> transformerData) {
}
