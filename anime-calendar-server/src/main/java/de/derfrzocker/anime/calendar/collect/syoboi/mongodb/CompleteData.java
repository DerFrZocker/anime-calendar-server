package de.derfrzocker.anime.calendar.collect.syoboi.mongodb;

import de.derfrzocker.anime.calendar.collect.syoboi.Channel;
import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.YearMonth;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "SyoboiCompleteData")
public class CompleteData {

    @BsonId
    public TID tid;
    public String title;
    public Channel firstChannel;
    public YearMonth firstStart;
    public YearMonth firstEnd;
}
