package de.derfrzocker.anime.calendar.utils;

import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.BoundLayerConfig;
import org.jetbrains.annotations.NotNull;

public final class LayerUtil {

    private LayerUtil() {
    }


    public static boolean shouldSkip(@NotNull EpisodeBuilder episodeBuilder, @NotNull BoundLayerConfig config) {
        if (episodeBuilder.episodeIndex() < config.getMinInclusive()) {
            return true;
        }

        return config.getMaxInclusive() != -1 && episodeBuilder.episodeIndex() > config.getMaxInclusive();
    }
}
