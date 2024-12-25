package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.layer.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.StreamingTimeLayerConfigParser;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
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
