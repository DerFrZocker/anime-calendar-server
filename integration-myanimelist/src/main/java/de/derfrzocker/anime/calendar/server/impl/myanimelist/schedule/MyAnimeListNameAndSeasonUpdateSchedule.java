package de.derfrzocker.anime.calendar.server.impl.myanimelist.schedule;

import de.derfrzocker.anime.calendar.server.impl.myanimelist.handler.MyAnimeListNameAndSeasonUpdateRequestHandler;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@RequestScoped
public class MyAnimeListNameAndSeasonUpdateSchedule {

    private static final UserId MY_ANIME_LIST_NAME_AND_SEASON_UPDATE_USER = new UserId("UMALNASEUP");

    @Inject
    MyAnimeListNameAndSeasonUpdateRequestHandler requestHandler;

    @Scheduled(cron = "{myanimelist.name-and-season-update.cron:off}")
    public void schedule() {
        this.requestHandler.createOrUpdate(new RequestContext(MY_ANIME_LIST_NAME_AND_SEASON_UPDATE_USER, Instant.now()))
                           .subscribe()
                           .asCompletionStage();
    }
}
