package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostSyoboiAnimeScheduleRetrieveEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
class SyoboiNewAnimeAndTimeUpdateTriggerListener {

    @Inject
    SyoboiNewAnimeAndTimeUpdateJob job;

    public void onPostSyoboiAnimeScheduleRetrieve(@Observes PostSyoboiAnimeScheduleRetrieveEvent event) {

        Log.infof(
                "Starting checking for new anime or updating airing time for '%d' schedule entries.",
                event.schedules().size());

        this.job
                .executeAsync(event.schedules(), event.context())
                .onFailure()
                .invoke(error -> Log.errorf(error, "Syoboi new anime or time update job failed."))
                .invoke(() -> Log.infof("Successfully checked for new anime or updated existing ones."))
                .subscribe()
                .asCompletionStage();
    }
}
