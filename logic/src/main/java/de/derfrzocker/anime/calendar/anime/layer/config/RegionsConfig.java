package de.derfrzocker.anime.calendar.anime.layer.config;

import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.Region;
import java.util.List;
import java.util.Objects;

public record RegionsConfig(List<Region> regions) implements LayerConfigData {

    public static final String DATA_TYPE_RAW = "regions";
    public static final LayerConfigDataType DATA_TYPE = LayerConfigDataType.of(DATA_TYPE_RAW);

    public RegionsConfig {
        regions = List.copyOf(Objects.requireNonNull(regions));
    }
}
