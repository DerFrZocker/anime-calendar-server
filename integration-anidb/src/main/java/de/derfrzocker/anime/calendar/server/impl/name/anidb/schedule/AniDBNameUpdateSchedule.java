package de.derfrzocker.anime.calendar.server.impl.name.anidb.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.impl.name.anidb.handler.AniDBNameUpdateRequestHandler;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class AniDBNameUpdateSchedule {

    private static final UserId ANIDB_NAME_UPDATE_USER = new UserId("UANIDBNAUP");

    @Inject
    AniDBNameUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{anidb.name-update.cron:off}", executionMaxDelay = "{anidb.name-update.jitter}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(ANIDB_NAME_UPDATE_USER, Instant.now()))
                           .subscribe()
                           .asCompletionStage();
    }
}
