package de.derfrzocker.anime.calendar.server.layer.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingTimeLayerConfig;
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
        if (animeOptions.streamTypes().isEmpty()) {
            return;
        }
        if (!animeOptions.streamTypes().contains(layerConfig.type())) {
            return;
        }
        if (episodeBuilder.type() != null && animeOptions.streamTypes().size() > 1) {
            int currentIndex = animeOptions.streamTypes().indexOf(episodeBuilder.type());
            int potentialIndex = animeOptions.streamTypes().indexOf(layerConfig.type());

            if (currentIndex < potentialIndex) {
                return;
            }
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
