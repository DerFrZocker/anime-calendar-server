package de.derfrzocker.anime.calendar.server.integration.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "integration.service.name-search")
public interface NameSearchServiceConfig {

    @WithName("distance-threshold")
    int distanceThreshold();
}
