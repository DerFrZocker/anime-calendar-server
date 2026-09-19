package de.derfrzocker.anime.calendar.anime.mongodb.model;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.metadata.mongodb.model.SimpleEntityMetadataDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;
import java.util.Objects;

@MongoEntity(collection = "anime_new")
public record AnimeDO(
        @BsonId AnimeId id,
        @BsonProperty("name") String name,
        @BsonProperty("episode_count") int episodeCount,
        @BsonProperty("episode_layers") List<LayerStepConfigDO> episodeLayers,
        @BsonProperty("metadata") SimpleEntityMetadataDO metadata) {

    public AnimeDO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(episodeLayers);
        Objects.requireNonNull(metadata);
    }
}
