package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

public record TIDDataCreateData(
        String title,
        ChannelId trackingChannelId,
        YearMonth firstStart,
        YearMonth firstEnd,
        List<ChannelId> firstChannelIds,
        boolean include,
        Instant validUntil) {

}
