package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.core.util.Change;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public record TIDDataUpdateData(Change<String> title, Change<@Nullable ChannelId> trackingChannelId,
                                Change<YearMonth> firstStart, Change<@Nullable YearMonth> firstEnd,
                                Change<@Nullable List<ChannelId>> firstChannelIds, Change<Boolean> include,
                                Change<@Nullable Instant> validUntil) {

}
