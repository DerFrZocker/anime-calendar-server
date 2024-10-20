package de.derfrzocker.anime.calendar.collect.anidb.schedule;

import de.derfrzocker.anime.calendar.collect.anidb.season.SeasonService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SeasonSchedule {

    @Inject
    SeasonService seasonService;

    public void updateSeason() {
        seasonService.updateSeason();
    }
}
