package de.derfrzocker.anime.calendar.anime.layer.filter;

import de.derfrzocker.anime.calendar.anime.LayerConfig;
import de.derfrzocker.anime.calendar.anime.Region;
import de.derfrzocker.anime.calendar.anime.layer.config.RegionsConfig;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleBoundConfig;
import java.util.List;

public final class Filters {

    private Filters() {
    }

    public static LayerConfig<SimpleBoundConfig> bound(int minInclusive, int maxInclusive) {
        return new LayerConfig<>(
                BoundFilter.LAYER_TYPE,
                SimpleBoundConfig.DATA_TYPE,
                new SimpleBoundConfig(minInclusive, maxInclusive));
    }

    public static LayerConfig<RegionsConfig> region(List<Region> regions) {
        return new LayerConfig<>(RegionFilter.LAYER_TYPE, RegionsConfig.DATA_TYPE, new RegionsConfig(regions));
    }
}
