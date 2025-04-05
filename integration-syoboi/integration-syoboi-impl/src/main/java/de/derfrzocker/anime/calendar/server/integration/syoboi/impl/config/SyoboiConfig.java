package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SyoboiConfig {

    @ConfigProperty(name = "syoboi-collection.incomplete-data-duration")
    Duration tidDataValidationDuration;
    @ConfigProperty(name = "syoboi.anime-create-and-update.days")
    int animeScheduleDays;
    @ConfigProperty(name = "syoboi.tracking-channel.valid-length")
    Duration trackingChannelActionValidLength;
    @ConfigProperty(name = "syoboi.new-anime.valid-length")
    Duration newAnimeActionValidLength;

    public Duration getTIDDataValidationDuration() {
        return this.tidDataValidationDuration;
    }

    public int getAnimeScheduleDays() {
        return this.animeScheduleDays;
    }

    public Duration getTrackingChannelActionValidLength() {
        return this.trackingChannelActionValidLength;
    }

    public Duration getNewAnimeActionValidLength() {
        return this.newAnimeActionValidLength;
    }
}
