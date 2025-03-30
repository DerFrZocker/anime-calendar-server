package de.derfrzocker.anime.calendar.server.mongodb.data.season;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.core.season.Season;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "AnimeSeasonInfo")
public class AnimeSeasonInfoDO extends ModificationInfoDO {

    @BsonId
    public ObjectId id;
    public IntegrationId integrationId;
    public IntegrationAnimeId integrationAnimeId;
    public int year;
    public Season season;
}
