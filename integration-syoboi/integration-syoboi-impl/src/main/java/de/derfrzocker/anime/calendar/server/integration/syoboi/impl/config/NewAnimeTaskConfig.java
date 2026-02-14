package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.time.Duration;

@ConfigMapping(prefix = "integration.syoboi.task.new-anime")
public interface NewAnimeTaskConfig {

    @WithName("notification.valid-duration")
    Duration validDuration();
}
