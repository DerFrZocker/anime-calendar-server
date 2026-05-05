package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "TIDData")
public class TIDDataDO extends ModificationInfoDO {

    @BsonId
    public TID tid;
    public String title;
    public ChannelId trackingChannelId;
    public YearMonth firstStart;
    public YearMonth firstEnd;
    public List<ChannelId> firstChannelIds;
    public boolean include;
    public Instant validUntil;
}
