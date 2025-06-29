package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigMapping(prefix = "integration.syoboi.schedule.anime-schedule-providing")
public interface SyoboiAnimeScheduleProvidingConfig {

    @WithName("days")
    int days();
}
