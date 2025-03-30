package de.derfrzocker.anime.calendar.server.impl.myanimelist.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.handler.MyAnimeListNameAndSeasonUpdateRequestHandler;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class MyAnimeListNameAndSeasonUpdateSchedule {

    private static final UserId MY_ANIME_LIST_NAME_AND_SEASON_UPDATE_USER = new UserId("UMALNASEUP");

    @Inject
    MyAnimeListNameAndSeasonUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{myanimelist.name-and-season-update.cron:off}",
               executionMaxDelay = "{myanimelist.name-and-season-update.jitter}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(MY_ANIME_LIST_NAME_AND_SEASON_UPDATE_USER, Instant.now()))
                           .subscribe()
                           .asCompletionStage();
    }
}
