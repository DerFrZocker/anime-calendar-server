package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public record TIDDataUpdateData(Change<String> title, Change<@Nullable Channel> trackingChannel,
                                Change<YearMonth> firstStart, Change<@Nullable YearMonth> firstEnd,
                                Change<@Nullable List<Channel>> firstChannels, Change<Boolean> include,
                                Change<@Nullable Instant> validUntil) {

}
