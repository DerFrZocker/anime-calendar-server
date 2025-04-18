package de.derfrzocker.anime.calendar.server.layer2.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer2.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer2.common.config.StreamingTimeLayerConfig;
import java.time.Period;
import org.jetbrains.annotations.NotNull;

public final class StreamingTimeLayerTransformer extends AbstractLayerTransformer<StreamingTimeLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("streaming-time");
    public static final StreamingTimeLayerTransformer INSTANCE = new StreamingTimeLayerTransformer();

    private StreamingTimeLayerTransformer() {
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull StreamingTimeLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        if (animeOptions.streamType() != null && !animeOptions.streamType().equals(layerConfig.type())) {
            return;
        }

        Period period = layerConfig.period().multipliedBy((episodeBuilder.episodeIndex() - layerConfig.offset()));

        episodeBuilder.withStreamingTime(layerConfig.startTime().plus(period));
        episodeBuilder.withType(layerConfig.type());
    }

    @Override
    protected Class<StreamingTimeLayerConfig> configClass() {
        return StreamingTimeLayerConfig.class;
    }
}
