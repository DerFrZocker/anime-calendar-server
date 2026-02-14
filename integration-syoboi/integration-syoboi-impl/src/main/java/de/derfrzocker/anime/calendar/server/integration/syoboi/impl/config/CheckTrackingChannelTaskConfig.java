package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.time.Duration;

@ConfigMapping(prefix = "integration.syoboi.task.check-tracking-channel")
public interface CheckTrackingChannelTaskConfig {

    @WithName("notification.valid-duration")
    Duration validDuration();
}
