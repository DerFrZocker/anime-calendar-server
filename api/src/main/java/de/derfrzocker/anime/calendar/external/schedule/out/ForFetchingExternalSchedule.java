package de.derfrzocker.anime.calendar.external.schedule.out;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.schedule.ExternalChannelId;
import de.derfrzocker.anime.calendar.external.schedule.ExternalScheduleEntry;
import io.smallrye.mutiny.Multi;

import java.time.LocalDate;

public interface ForFetchingExternalSchedule {

    Multi<ExternalScheduleEntry> fetchSchedule(LocalDate start, int days, RequestContext context);

    Multi<ExternalScheduleEntry> fetchSchedule(
            ExternalAnimeId animeId,
            ExternalChannelId channelId,
            RequestContext context);
}
