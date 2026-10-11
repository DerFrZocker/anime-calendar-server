package de.derfrzocker.anime.calendar.anime.layer.filter;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerFilter;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.layer.config.RegionsConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@Identifier(RegionFilter.LAYER_TYPE_RAW)
@ApplicationScoped
public class RegionFilter implements LayerFilter<RegionsConfig> {

    public static final String LAYER_TYPE_RAW = "region-filter";
    public static final LayerType LAYER_TYPE = new LayerType(LAYER_TYPE_RAW);

    @Override
    public boolean shouldSkip(Anime anime, EpisodeBuildOption option, EpisodeBuilder builder, RegionsConfig config) {
        return !config.regions().contains(option.region());
    }
}
