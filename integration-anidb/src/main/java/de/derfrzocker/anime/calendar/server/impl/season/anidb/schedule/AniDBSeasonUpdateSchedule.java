package de.derfrzocker.anime.calendar.server.impl.season.anidb.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.impl.season.anidb.handler.AniDBSeasonUpdateRequestHandler;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class AniDBSeasonUpdateSchedule {

    private static final UserId ANIDB_SEASON_UPDATE_USER = new UserId("UANIDBSEUP");

    @Inject
    AniDBSeasonUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{anidb.season-update.cron:off}", executionMaxDelay = "{anidb.season-update.jitter}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(ANIDB_SEASON_UPDATE_USER, Instant.now()))
                           .subscribe()
                           .asCompletionStage();
    }
}
