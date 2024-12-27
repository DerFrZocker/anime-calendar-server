package de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data;

import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.Channel;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.YearMonth;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "SyoboiCompleteData")
public class SyoboiCompleteDataDO {

    @BsonId
    public TID tid;
    public String title;
    public Channel firstChannel;
    public YearMonth firstStart;
    public YearMonth firstEnd;
    public boolean include;
}
