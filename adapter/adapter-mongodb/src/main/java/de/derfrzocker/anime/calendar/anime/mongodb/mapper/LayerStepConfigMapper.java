package de.derfrzocker.anime.calendar.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.anime.LayerConfig;
import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.LayerStepConfig;
import de.derfrzocker.anime.calendar.anime.mongodb.model.LayerConfigDO;
import de.derfrzocker.anime.calendar.anime.mongodb.model.LayerStepConfigDO;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public final class LayerStepConfigMapper {

    private LayerStepConfigMapper() {

    }

    public static LayerStepConfig toDomain(
            LayerStepConfigDO data,
            BiFunction<LayerConfigDataType, BsonDocument, LayerConfigData> configDataMapper) {

        return new LayerStepConfig(
                toDomain(data.filters(), configDataMapper),
                LayerConfigMapper.toDomain(data.transformer(), configDataMapper));
    }

    public static LayerStepConfigDO toData(
            LayerStepConfig domain,
            BiFunction<LayerConfigDataType, LayerConfigData, BsonDocument> configDataMapper) {

        return new LayerStepConfigDO(
                domain.filters().stream().map(d -> LayerConfigMapper.toData(d, configDataMapper)).toList(),
                LayerConfigMapper.toData(domain.transformer(), configDataMapper));
    }

    private static List<LayerConfig<?>> toDomain(
            List<LayerConfigDO> data,
            BiFunction<LayerConfigDataType, BsonDocument, LayerConfigData> configDataMapper) {

        List<LayerConfig<?>> result = new ArrayList<>();
        for (LayerConfigDO entry : data) {
            result.add(LayerConfigMapper.toDomain(entry, configDataMapper));
        }

        return Collections.unmodifiableList(result);
    }
}
