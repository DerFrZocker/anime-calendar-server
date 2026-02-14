package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigMapping(prefix = "integration.syoboi.schedule.anime-program-provider")
public interface SyoboiAnimeProgramProviderConfig {

    @WithName("program-days")
    int programDays();

    // TODO 2026-02-14: Dummy property refactor schedule class so that it does not need programDays
    @WithName("cron")
    String cron();

    // TODO 2026-02-14: Dummy property refactor schedule class so that it does not need programDays
    @WithName("jitter")
    String jitter();
}
