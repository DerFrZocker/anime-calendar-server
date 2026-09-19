package de.derfrzocker.anime.calendar.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.mongodb.model.AnimeDO;
import de.derfrzocker.anime.calendar.metadata.mongodb.mapper.SimpleEntityMetadataMapper;
import org.bson.BsonDocument;

import java.util.function.BiFunction;

public final class AnimeMapper {

    private AnimeMapper() {

    }

    public static Anime toDomain(
            AnimeDO data,
            BiFunction<LayerConfigDataType, BsonDocument, LayerConfigData> configDataMapper) {

        return new Anime(
                data.id(),
                data.name(),
                data.episodeCount(),
                data.episodeLayers()
                        .stream()
                        .map(config -> LayerStepConfigMapper.toDomain(config, configDataMapper))
                        .toList(),
                SimpleEntityMetadataMapper.toDomain(data.metadata()));
    }

    public static AnimeDO toData(
            Anime domain,
            BiFunction<LayerConfigDataType, LayerConfigData, BsonDocument> configDataMapper) {

        return new AnimeDO(
                domain.id(),
                domain.name(),
                domain.episodeCount(),
                domain.episodeLayers()
                        .stream()
                        .map(config -> LayerStepConfigMapper.toData(config, configDataMapper))
                        .toList(),
                SimpleEntityMetadataMapper.toData(domain.metadata()));
    }
}
