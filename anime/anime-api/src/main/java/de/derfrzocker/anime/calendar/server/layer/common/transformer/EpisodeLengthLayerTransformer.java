package de.derfrzocker.anime.calendar.server.layer.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleIntegerLayerConfig;
import org.jetbrains.annotations.NotNull;

public final class EpisodeLengthLayerTransformer extends AbstractLayerTransformer<SimpleIntegerLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("episode-length");
    public static final EpisodeLengthLayerTransformer INSTANCE = new EpisodeLengthLayerTransformer();

    private EpisodeLengthLayerTransformer() {
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull SimpleIntegerLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeLength(layerConfig.value());
    }

    @Override
    protected Class<SimpleIntegerLayerConfig> configClass() {
        return SimpleIntegerLayerConfig.class;
    }
}
