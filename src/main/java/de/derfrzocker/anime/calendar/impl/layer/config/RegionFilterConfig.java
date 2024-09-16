package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public record RegionFilterConfig(Set<@NotNull Region> applicableRegions) implements LayerConfig {

}
