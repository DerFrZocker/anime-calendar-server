package de.derfrzocker.anime.calendar.utils;

import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.BoundFilterConfig;
import org.jetbrains.annotations.NotNull;

public final class LayerUtil {

    private LayerUtil() {
    }


    public static boolean shouldSkip(@NotNull EpisodeBuilder episodeBuilder, @NotNull BoundFilterConfig config) {
        if (episodeBuilder.episodeIndex() < config.minInclusive()) {
            return true;
        }

        return config.maxInclusive() != -1 && episodeBuilder.episodeIndex() > config.maxInclusive();
    }
}
