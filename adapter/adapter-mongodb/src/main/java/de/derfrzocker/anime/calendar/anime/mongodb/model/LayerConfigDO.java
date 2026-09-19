package de.derfrzocker.anime.calendar.anime.mongodb.model;

import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.LayerType;
import org.bson.BsonDocument;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

public record LayerConfigDO(
        @BsonProperty("type") LayerType type,
        @BsonProperty("data_type") LayerConfigDataType dataType,
        @BsonProperty("data") BsonDocument data) {

    public LayerConfigDO {
        Objects.requireNonNull(type);
        Objects.requireNonNull(dataType);
        Objects.requireNonNull(data);
    }
}
