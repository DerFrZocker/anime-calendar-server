package de.derfrzocker.anime.calendar.plugin.mongodb.anime;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;
import java.util.Map;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Anime")
public class AnimeDO {

    @BsonId
    public AnimeId animeId;
    public String animeTitle;
    public int episodeCount;
    public List<Map<String, Object>> episodeLayers;

}
