package de.derfrzocker.anime.calendar.integration.mongodb;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "IntegrationAnimeId")
public class IntegrationAnimeIdDO {

    @BsonId
    public ObjectId id;
    public IntegrationId integrationId;
    public AnimeId animeId;
    public IntegrationAnimeId integrationAnimeId;
}
