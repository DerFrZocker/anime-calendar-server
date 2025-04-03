package de.derfrzocker.anime.calendar.server.anime.mongodb.data;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;
import java.util.Map;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Anime")
public class AnimeDO extends ModificationInfoDO {

    @BsonId
    public AnimeId id;
    public String title;
    @Deprecated(forRemoval = true)
    public String animeTitle;
    public int episodeCount;
    public List<Map<String, Object>> episodeLayers;
}
