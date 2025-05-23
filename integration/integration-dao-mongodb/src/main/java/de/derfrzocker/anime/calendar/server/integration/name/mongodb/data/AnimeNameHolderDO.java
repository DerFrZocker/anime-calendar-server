package de.derfrzocker.anime.calendar.server.integration.name.mongodb.data;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "AnimeNameHolder")
public class AnimeNameHolderDO extends ModificationInfoDO {

    @BsonId
    public ObjectId id;
    public IntegrationId integrationId;
    public IntegrationAnimeId integrationAnimeId;
    public List<AnimeNameDO> names;
}
