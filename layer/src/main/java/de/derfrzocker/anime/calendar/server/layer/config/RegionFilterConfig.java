package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.model.domain.ical.Region;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public record RegionFilterConfig(Set<@NotNull Region> applicableRegions) implements LayerConfig {

}
