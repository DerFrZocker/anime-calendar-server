package de.derfrzocker.anime.calendar.server.layer2.common.transformer;

import com.spencerwi.either.Either;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer2.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer2.common.config.SimpleOffsetIntegerLayerConfig;
import org.jetbrains.annotations.NotNull;

public final class EpisodeNumberLayerTransformer extends AbstractLayerTransformer<SimpleOffsetIntegerLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("episode-number");
    public static final EpisodeNumberLayerTransformer INSTANCE = new EpisodeNumberLayerTransformer();

    private EpisodeNumberLayerTransformer() {
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull SimpleOffsetIntegerLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeNumber(Either.right((episodeBuilder.episodeIndex() - layerConfig.offset()) + layerConfig.value()));
    }

    @Override
    protected Class<SimpleOffsetIntegerLayerConfig> configClass() {
        return SimpleOffsetIntegerLayerConfig.class;
    }
}
