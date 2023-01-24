package de.derfrzocker.anime.calendar.api;

import de.derfrzocker.anime.calendar.api.transformer.TransformerData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Anime(int animeId, @NotNull String animeName, int episodeCount,
                    @NotNull List<@NotNull TransformerData<?>> transformerData) {
}
