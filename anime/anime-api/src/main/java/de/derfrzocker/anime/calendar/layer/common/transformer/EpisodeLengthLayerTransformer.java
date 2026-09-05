package de.derfrzocker.anime.calendar.layer.common.transformer;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.layer.common.config.SimpleIntegerLayerConfig;

public final class EpisodeLengthLayerTransformer extends AbstractLayerTransformer<SimpleIntegerLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("episode-length");
    public static final EpisodeLengthLayerTransformer INSTANCE = new EpisodeLengthLayerTransformer();

    private EpisodeLengthLayerTransformer() {
    }

    @Override
    public void transform(
            Anime anime,
            AnimeOptions animeOptions,
            SimpleIntegerLayerConfig layerConfig,
            EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeLength(layerConfig.value());
    }

    @Override
    protected Class<SimpleIntegerLayerConfig> configClass() {
        return SimpleIntegerLayerConfig.class;
    }
}
