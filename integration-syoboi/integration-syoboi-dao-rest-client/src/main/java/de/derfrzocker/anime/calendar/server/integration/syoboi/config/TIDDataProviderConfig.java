package de.derfrzocker.anime.calendar.server.integration.syoboi.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "integration.syoboi.rest-client.tid-data-provider")
public interface TIDDataProviderConfig {

    @WithName("program-days")
    int programDays();
}
