package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfig;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public record RegionFilterConfig(Set<@NotNull Region> applicableRegions) implements LayerConfig {

}
