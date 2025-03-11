package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.schedule;

import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.handler.SyoboiAnimeCreateAndUpdateRequestHandler;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class SyoboiAnimeCreateAndUpdateSchedule {

    private static final UserId SYOBOI_ANIME_CREATE_AND_UPDATE_UPDATE_USER = new UserId("USYANIMCAU");

    @Inject
    SyoboiAnimeCreateAndUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{syoboi.anime-create-and-update.cron:off}",
               executionMaxDelay = "{syoboi.anime-create-and-update.jitter}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(SYOBOI_ANIME_CREATE_AND_UPDATE_UPDATE_USER,
                                                              Instant.now())).subscribe().asCompletionStage();
    }
}
