package de.derfrzocker.anime.calendar.server.integration.impl.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.integration.impl.task.CheckForMissingStreamingTask;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class CheckForMissingStreamingSchedule {

    private static final UserId MISSING_STREAMING_USER = UserId.of("UMISSTREAM");

    @Inject
    CheckForMissingStreamingTask checkForMissingStreamingTask;

    @Scheduled(cron = "{integration.search-missing-streaming.cron:off}",
               executionMaxDelay = "{integration.search-missing-streaming.jitter}")
    public void schedule() {
        RequestContext context = new RequestContext(MISSING_STREAMING_USER, Instant.now());
        this.checkForMissingStreamingTask.checkForMissingStreaming(context).subscribe().asCompletionStage();
    }
}
