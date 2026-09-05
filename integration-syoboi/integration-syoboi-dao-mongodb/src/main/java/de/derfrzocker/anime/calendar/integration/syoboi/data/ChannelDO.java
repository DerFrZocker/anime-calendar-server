package de.derfrzocker.anime.calendar.integration.syoboi.data;

import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Channel")
public class ChannelDO extends ModificationInfoDO {

    @BsonId
    public ChannelId id;
    public String name;
}
