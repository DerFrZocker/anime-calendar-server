package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public record ResolvedTIDData(TID tid, String title, @Nullable ChannelId trackingChannelId, YearMonth start,
                              @Nullable YearMonth end, List<ChannelId> channelIds, boolean include,
                              Instant validUntil) {

    public static ResolvedTIDData from(TIDData data) {
        return new ResolvedTIDData(data.tid(),
                                   data.title(),
                                   data.trackingChannelId(),
                                   data.firstStart(),
                                   data.firstEnd(),
                                   data.firstChannelIds(),
                                   data.include(),
                                   data.validUntil());
    }
}
