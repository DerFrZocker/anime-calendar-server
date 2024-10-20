package de.derfrzocker.anime.calendar.collect.anidb.mongodb;

import de.derfrzocker.anime.calendar.collect.anidb.AnimeNames;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "AniDBAnimeNamesList")
public class AnimeNamesListDO {

    @BsonId
    public ObjectId _id;
    public Instant validUntil;
    public List<AnimeNames> animeNames;
}
