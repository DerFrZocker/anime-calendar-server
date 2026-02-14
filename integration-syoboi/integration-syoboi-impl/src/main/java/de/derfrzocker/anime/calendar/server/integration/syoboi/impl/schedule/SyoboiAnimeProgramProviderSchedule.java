package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostSyoboiAnimeScheduleRetrieveEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.SyoboiAnimeProgramProviderConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.AnimeScheduleProviderService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class SyoboiAnimeProgramProviderSchedule {

    private static final UserId SYOBOI_ANIME_CREATE_AND_UPDATE_UPDATE_USER = UserId.of("USYANIMCAU");

    @Inject
    AnimeScheduleProviderService scheduleProviderService;
    @Inject
    SyoboiAnimeProgramProviderConfig config;
    @Inject
    Event<PostSyoboiAnimeScheduleRetrieveEvent> event;

    @Scheduled(cron = "{integration.syoboi.schedule.anime-program-provider.cron:off}",
               executionMaxDelay = "{integration.syoboi.schedule.anime-program-provider.jitter}")
    public void schedule() {
        RequestContext context = new RequestContext(SYOBOI_ANIME_CREATE_AND_UPDATE_UPDATE_USER, Instant.now());
        List<ProvidedAnimeSchedule> schedules;

        try (var stream = getScheduleData(context)) {
            schedules = stream.toList();
        }

        this.event.fire(new PostSyoboiAnimeScheduleRetrieveEvent(schedules, context));
    }

    private Stream<ProvidedAnimeSchedule> getScheduleData(RequestContext context) {
        return this.scheduleProviderService.provideAllWithData(LocalDate.now(), this.config.programDays(), context);
    }
}
