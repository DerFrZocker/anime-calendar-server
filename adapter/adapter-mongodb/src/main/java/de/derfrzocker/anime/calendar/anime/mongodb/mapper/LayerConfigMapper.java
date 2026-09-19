package de.derfrzocker.anime.calendar.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.anime.LayerConfig;
import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.mongodb.model.LayerConfigDO;
import org.bson.BsonDocument;

import java.util.function.BiFunction;

public final class LayerConfigMapper {

    private LayerConfigMapper() {
    }

    public static LayerConfig<?> toDomain(
            LayerConfigDO data,
            BiFunction<LayerConfigDataType, BsonDocument, LayerConfigData> configDataMapper) {

        return new LayerConfig<>(data.type(), data.dataType(), configDataMapper.apply(data.dataType(), data.data()));
    }

    public static LayerConfigDO toData(
            LayerConfig<?> config,
            BiFunction<LayerConfigDataType, LayerConfigData, BsonDocument> configDataMapper) {

        return new LayerConfigDO(
                config.type(),
                config.dataType(),
                configDataMapper.apply(config.dataType(), config.data()));
    }
}
