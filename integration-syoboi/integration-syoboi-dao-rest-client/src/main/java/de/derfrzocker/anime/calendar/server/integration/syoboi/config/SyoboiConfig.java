package de.derfrzocker.anime.calendar.server.integration.syoboi.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SyoboiConfig {

    @ConfigProperty(name = "syoboi.anime-create-and-update.days")
    int animeScheduleDays;

    public int getAnimeScheduleDays() {
        return this.animeScheduleDays;
    }
}
