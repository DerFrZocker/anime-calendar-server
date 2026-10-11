package de.derfrzocker.anime.calendar.anime.layer.transformer;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerTransformer;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.layer.config.EmptyConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@Identifier(EpisodesTransformer.LAYER_TYPE_RAW)
@ApplicationScoped
class EpisodesTransformer implements LayerTransformer<EmptyConfig> {

    public static final String LAYER_TYPE_RAW = "episodes-transformer";
    public static final LayerType LAYER_TYPE = new LayerType(LAYER_TYPE_RAW);

    @Override
    public void transform(Anime anime, EpisodeBuildOption option, EpisodeBuilder builder, EmptyConfig config) {

        builder.episodes(String.valueOf(anime.episodeCount()));
    }
}
