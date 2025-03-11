package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public record TIDDataCreateData(String title, @Nullable Channel trackingChannel, YearMonth firstStart,
                                @Nullable YearMonth firstEnd, List<Channel> firstChannels, boolean include,
                                @Nullable Instant validUntil) {

}
