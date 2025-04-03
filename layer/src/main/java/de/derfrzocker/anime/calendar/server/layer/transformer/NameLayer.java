package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.config.SimpleStringLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.SimpleStringLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class NameLayer extends AbstractEpisodeTransformer<SimpleStringLayerConfig> {

    public static final NameLayer INSTANCE = new NameLayer();

    private NameLayer() {
        super("name", SimpleStringLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull SimpleStringLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeName(layerConfig.value());
    }
}
