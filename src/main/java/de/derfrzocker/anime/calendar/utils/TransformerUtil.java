package de.derfrzocker.anime.calendar.utils;

import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.BoundTransformerConfig;
import org.jetbrains.annotations.NotNull;

public final class TransformerUtil {

    private TransformerUtil() {
    }


    public static boolean shouldSkip(@NotNull EpisodeBuilder episodeBuilder, @NotNull BoundTransformerConfig config) {
        if (episodeBuilder.episodeIndex() < config.getMinInclusive()) {
            return true;
        }

        return config.getMaxInclusive() != -1 && episodeBuilder.episodeIndex() > config.getMaxInclusive();
    }
}
