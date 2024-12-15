package de.derfrzocker.anime.calendar.impl.layer.transformer;

import de.derfrzocker.anime.calendar.impl.layer.config.SimpleStringLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.SimpleStringLayerConfigParser;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import org.jetbrains.annotations.NotNull;

public final class NameLayer extends AbstractEpisodeTransformer<SimpleStringLayerConfig> {

    public static final NameLayer INSTANCE = new NameLayer();

    private NameLayer() {
        super("name", SimpleStringLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleStringLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeName(layerConfig.value());
    }
}
