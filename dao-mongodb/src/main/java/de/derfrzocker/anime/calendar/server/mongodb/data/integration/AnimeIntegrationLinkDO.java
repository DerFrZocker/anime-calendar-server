package de.derfrzocker.anime.calendar.server.mongodb.data.integration;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "AnimeIntegrationLink")
public class AnimeIntegrationLinkDO extends ModificationInfoDO {

    @BsonId
    public ObjectId id;
    public AnimeId animeId;
    public IntegrationId integrationId;
    public IntegrationAnimeId integrationAnimeId;
}
