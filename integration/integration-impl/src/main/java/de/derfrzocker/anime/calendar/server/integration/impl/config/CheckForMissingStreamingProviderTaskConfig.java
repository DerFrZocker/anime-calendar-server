package de.derfrzocker.anime.calendar.server.integration.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.time.Duration;

@ConfigMapping(prefix = "integration.task.check-for-missing-streaming-provider")
public interface CheckForMissingStreamingProviderTaskConfig {

    @WithName("notification.valid-duration")
    Duration validDuration();
}
