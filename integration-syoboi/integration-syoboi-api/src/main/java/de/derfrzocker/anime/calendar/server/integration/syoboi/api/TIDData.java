package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public record TIDData(TID tid, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy, String title,
                      @Nullable ChannelId trackingChannelId, YearMonth firstStart, @Nullable YearMonth firstEnd,
                      List<ChannelId> firstChannelIds, boolean include,
                      @Nullable Instant validUntil) implements ModificationInfo {

    public static TIDData from(TID tid, TIDDataCreateData createData, RequestContext context) {
        return new TIDData(tid,
                           context.requestTime(),
                           context.requestUser(),
                           context.requestTime(),
                           context.requestUser(),
                           createData.title(),
                           createData.trackingChannelId(),
                           createData.firstStart(),
                           createData.firstEnd(),
                           createData.firstChannelIds(),
                           createData.include(),
                           createData.validUntil());
    }

    public TIDData updateWithData(TIDDataUpdateData updateData, RequestContext context) {
        return new TIDData(tid(),
                           createdAt(),
                           createdBy(),
                           context.requestTime(),
                           context.requestUser(),
                           updateData.title().apply(title()),
                           updateData.trackingChannelId().apply(trackingChannelId()),
                           updateData.firstStart().apply(firstStart()),
                           updateData.firstEnd().apply(firstEnd()),
                           updateData.firstChannelIds().apply(firstChannelIds()),
                           updateData.include().apply(include()),
                           updateData.validUntil().apply(validUntil()));
    }
}
