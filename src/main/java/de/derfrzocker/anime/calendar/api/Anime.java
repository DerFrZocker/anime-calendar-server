package de.derfrzocker.anime.calendar.api;

import de.derfrzocker.anime.calendar.api.layer.LayerDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Anime(String animeId, @NotNull String animeName, int episodeCount,
                    @NotNull List<@NotNull LayerDataHolder<?>> transformerData) {
}
