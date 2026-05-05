package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

public record ResolvedTIDData(
        TID tid,
        String title,
        ChannelId trackingChannelId,
        YearMonth start,
        YearMonth end,
        List<ChannelId> channelIds,
        boolean include,
        Instant validUntil) {

    public static ResolvedTIDData from(TIDData data) {
        return new ResolvedTIDData(
                data.tid(),
                data.title(),
                data.trackingChannelId(),
                data.firstStart(),
                data.firstEnd(),
                data.firstChannelIds(),
                data.include(),
                data.validUntil());
    }
}
