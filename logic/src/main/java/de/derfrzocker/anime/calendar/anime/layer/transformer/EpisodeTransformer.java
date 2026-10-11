package de.derfrzocker.anime.calendar.anime.layer.transformer;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerTransformer;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleOffsetIntegerConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@Identifier(EpisodeTransformer.LAYER_TYPE_RAW)
@ApplicationScoped
class EpisodeTransformer implements LayerTransformer<SimpleOffsetIntegerConfig> {

    public static final String LAYER_TYPE_RAW = "episode-transformer";
    public static final LayerType LAYER_TYPE = new LayerType(LAYER_TYPE_RAW);

    /**
     * Converts the builder's zero-based episode index into the configured episode numbering.
     * The offset identifies the index that should correspond to the configured starting value;
     * indices above or below it increase or decrease the episode number accordingly. The result
     * is calculated as {@code index - offset + value}.
     */
    @Override
    public void transform(
            Anime anime,
            EpisodeBuildOption option,
            EpisodeBuilder builder,
            SimpleOffsetIntegerConfig config) {

        int episode = builder.index() - config.offset() + config.value();

        builder.episode(String.valueOf(episode));
    }
}
