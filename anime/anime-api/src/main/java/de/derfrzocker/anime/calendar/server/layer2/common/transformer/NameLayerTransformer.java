package de.derfrzocker.anime.calendar.server.layer2.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer2.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer2.common.config.SimpleStringLayerConfig;
import org.jetbrains.annotations.NotNull;

public final class NameLayerTransformer extends AbstractLayerTransformer<SimpleStringLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("name");
    public static final NameLayerTransformer INSTANCE = new NameLayerTransformer();

    private NameLayerTransformer() {
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull SimpleStringLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeName(layerConfig.value());
    }

    @Override
    protected Class<SimpleStringLayerConfig> configClass() {
        return SimpleStringLayerConfig.class;
    }
}
