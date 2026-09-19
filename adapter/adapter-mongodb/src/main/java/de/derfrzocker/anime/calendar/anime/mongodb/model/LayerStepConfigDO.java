package de.derfrzocker.anime.calendar.anime.mongodb.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;
import java.util.Objects;

public record LayerStepConfigDO(
        @BsonProperty("filters") List<LayerConfigDO> filters, @BsonProperty("transformer") LayerConfigDO transformer) {

    public LayerStepConfigDO {
        Objects.requireNonNull(filters);
        Objects.requireNonNull(transformer);
    }
}
