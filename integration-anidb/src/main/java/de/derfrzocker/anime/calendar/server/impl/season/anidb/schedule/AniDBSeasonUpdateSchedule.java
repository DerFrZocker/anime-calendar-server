package de.derfrzocker.anime.calendar.server.impl.season.anidb.schedule;

import de.derfrzocker.anime.calendar.server.impl.season.anidb.handler.AniDBSeasonUpdateRequestHandler;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@RequestScoped
public class AniDBSeasonUpdateSchedule {

    private static final UserId ANIDB_SEASON_UPDATE_USER = new UserId("UANIDBSEUP");

    @Inject
    AniDBSeasonUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{anidb.season-update.cron:off}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(ANIDB_SEASON_UPDATE_USER, Instant.now()))
                           .subscribe()
                           .asCompletionStage();
    }
}
