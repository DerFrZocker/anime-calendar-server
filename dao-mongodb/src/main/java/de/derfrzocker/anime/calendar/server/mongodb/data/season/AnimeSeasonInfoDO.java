package de.derfrzocker.anime.calendar.server.mongodb.data.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import de.derfrzocker.anime.calendar.server.mongodb.data.ModificationInfoDO;
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
