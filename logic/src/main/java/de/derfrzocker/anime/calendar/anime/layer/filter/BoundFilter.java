package de.derfrzocker.anime.calendar.anime.layer.filter;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerFilter;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleBoundConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@Identifier(BoundFilter.LAYER_TYPE_RAW)
@ApplicationScoped
class BoundFilter implements LayerFilter<SimpleBoundConfig> {

    public static final String LAYER_TYPE_RAW = "bound-filter";
    public static final LayerType LAYER_TYPE = new LayerType(LAYER_TYPE_RAW);

    @Override
    public boolean shouldSkip(
            Anime anime,
            EpisodeBuildOption option,
            EpisodeBuilder builder,
            SimpleBoundConfig config) {

        if (builder.index() < config.minInclusive()) {
            return true;
        }

        if (config.maxInclusive() == -1) {
            return false;
        }

        return builder.index() > config.maxInclusive();
    }
}
