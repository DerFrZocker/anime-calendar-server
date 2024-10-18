package de.derfrzocker.anime.calendar.impl.layer.transformer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.StreamingTimeLayerConfigParser;
import org.jetbrains.annotations.NotNull;

import java.time.Period;

public final class StreamingTimeLayer extends AbstractEpisodeTransformer<StreamingTimeLayerConfig> {

    public static final StreamingTimeLayer INSTANCE = new StreamingTimeLayer();

    private StreamingTimeLayer() {
        super("streaming-time", StreamingTimeLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull StreamingTimeLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (animeOptions.streamType() != null && !animeOptions.streamType().equals(layerConfig.type())) {
            return;
        }

        Period period = layerConfig.period().multipliedBy((episodeBuilder.episodeIndex() - layerConfig.offset()));

        episodeBuilder.withStreamingTime(layerConfig.startTime().plus(period));
        episodeBuilder.withType(layerConfig.type());
    }
}
