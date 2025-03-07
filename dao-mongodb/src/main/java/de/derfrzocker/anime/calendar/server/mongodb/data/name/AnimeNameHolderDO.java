package de.derfrzocker.anime.calendar.server.mongodb.data.name;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
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
