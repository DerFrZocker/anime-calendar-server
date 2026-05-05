package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.core.util.Change;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

public record TIDDataUpdateData(
        Change<String> title,
        Change<ChannelId> trackingChannelId,
        Change<YearMonth> firstStart,
        Change<YearMonth> firstEnd,
        Change<List<ChannelId>> firstChannelIds,
        Change<Boolean> include,
        Change<Instant> validUntil) {

}
