package de.derfrzocker.anime.calendar.server.impl.namelink.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.impl.namelink.handler.NameLinkRequestHandler;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class NameLinkSchedule {

    private static final UserId NAME_LINK_UPDATE_USER = UserId.of("UNAMELINUP");

    @Inject
    NameLinkRequestHandler requestHandler;
    @Inject
    AnimeService animeService;

    @Scheduled(cron = "{integration.name-link.schedule.name-link.cron:off}",
               executionMaxDelay = "{integration.name-link.schedule.name-link.jitter}")
    public void schedule() {
        RequestContext context = new RequestContext(NAME_LINK_UPDATE_USER, Instant.now());
        this.animeService.getAll(context).forEach(anime -> {
            this.requestHandler.checkForLinks(anime, context).subscribe().asCompletionStage();
        });
    }
}
