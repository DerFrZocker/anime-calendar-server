package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.jetbrains.annotations.Nullable;

@MongoEntity(collection = "TIDData")
public class TIDDataDO extends ModificationInfoDO {

    @BsonId
    public TID tid;
    public String title;
    public @Nullable ChannelId trackingChannelId;
    public YearMonth firstStart;
    public @Nullable YearMonth firstEnd;
    public List<ChannelId> firstChannelIds;
    public boolean include;
    public @Nullable Instant validUntil;
}
