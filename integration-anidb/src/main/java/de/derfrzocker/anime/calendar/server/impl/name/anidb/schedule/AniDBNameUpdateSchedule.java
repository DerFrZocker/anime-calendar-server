package de.derfrzocker.anime.calendar.server.impl.name.anidb.schedule;

import de.derfrzocker.anime.calendar.server.impl.name.anidb.handler.AniDBNameUpdateRequestHandler;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@RequestScoped
public class AniDBNameUpdateSchedule {

    private static final UserId ANIDB_NAME_UPDATE_USER = new UserId("UANIDBNAUP");

    @Inject
    AniDBNameUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{anidb.name-update.cron:off}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(ANIDB_NAME_UPDATE_USER, Instant.now()))
                           .subscribe()
                           .asCompletionStage();
    }
}
