package de.derfrzocker.anime.calendar.server.integration.mongodb.data;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
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
