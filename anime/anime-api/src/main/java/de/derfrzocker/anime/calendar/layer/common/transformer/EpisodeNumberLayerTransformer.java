package de.derfrzocker.anime.calendar.layer.common.transformer;

import com.spencerwi.either.Either;
import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.layer.common.config.SimpleOffsetIntegerLayerConfig;

public final class EpisodeNumberLayerTransformer extends AbstractLayerTransformer<SimpleOffsetIntegerLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("episode-number");
    public static final EpisodeNumberLayerTransformer INSTANCE = new EpisodeNumberLayerTransformer();

    private EpisodeNumberLayerTransformer() {
    }

    @Override
    public void transform(
            Anime anime,
            AnimeOptions animeOptions,
            SimpleOffsetIntegerLayerConfig layerConfig,
            EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeNumber(Either.right((episodeBuilder.episodeIndex() - layerConfig.offset()) +
                                                      layerConfig.value()));
    }

    @Override
    protected Class<SimpleOffsetIntegerLayerConfig> configClass() {
        return SimpleOffsetIntegerLayerConfig.class;
    }
}
