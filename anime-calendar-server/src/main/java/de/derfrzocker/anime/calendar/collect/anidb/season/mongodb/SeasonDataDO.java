package de.derfrzocker.anime.calendar.collect.anidb.season.mongodb;

import de.derfrzocker.anime.calendar.collect.anidb.ExternalAnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "AniDBSeasonDataDO")
public class SeasonDataDO {

    @BsonId
    public ObjectId _id;
    public ExternalAnimeId externalAnimeId;
    public Instant startDate;
    public Season season;
    public int year;
}
