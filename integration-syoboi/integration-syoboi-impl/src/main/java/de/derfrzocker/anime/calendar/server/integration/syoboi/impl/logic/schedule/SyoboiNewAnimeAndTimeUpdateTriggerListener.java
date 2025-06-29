package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostSyoboiAnimeScheduleRetrieveEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
class SyoboiNewAnimeAndTimeUpdateTriggerListener {

    private static final Logger LOG = Logger.getLogger(SyoboiNewAnimeAndTimeUpdateTriggerListener.class);

    @Inject
    SyoboiNewAnimeAndTimeUpdateJob job;

    public void onPostSyoboiAnimeScheduleRetrieve(@Observes PostSyoboiAnimeScheduleRetrieveEvent event) {

        LOG.infov("Starting checking for new anime or updating airing time for {0} schedule entries.",
                  event.schedules().size());

        this.job.executeAsync(event.schedules(), event.context())
                .onFailure()
                .invoke(e -> LOG.errorv("Syoboi new anime or time update job failed.", e))
                .invoke(() -> LOG.infov("Successfully checked for new anime or updated existing ones."))
                .subscribe()
                .asCompletionStage();
    }
}
