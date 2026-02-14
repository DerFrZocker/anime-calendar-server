package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.time.Duration;

@ConfigMapping(prefix = "integration.syoboi.service.resolved-tid-data")
public interface ResolvedTIDDataServiceConfig {

    @WithName("valid-duration")
    Duration validDuration();
}
