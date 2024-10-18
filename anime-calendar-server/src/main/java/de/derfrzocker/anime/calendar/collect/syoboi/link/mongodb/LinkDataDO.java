package de.derfrzocker.anime.calendar.collect.syoboi.link.mongodb;

import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "SyoboiLinkData")
public class LinkDataDO {

    @BsonId
    public TID tid;
    public AnimeId animeId;
}
