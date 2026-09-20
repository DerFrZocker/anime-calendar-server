package de.derfrzocker.anime.calendar.external.mongodb.mapper;

import de.derfrzocker.anime.calendar.external.ExternalAnimeLink;
import de.derfrzocker.anime.calendar.external.mongodb.model.ExternalAnimeLinkDO;
import de.derfrzocker.anime.calendar.metadata.mongodb.mapper.SimpleEntityMetadataMapper;

public final class ExternalAnimeLinkMapper {

    private ExternalAnimeLinkMapper() {

    }

    public static ExternalAnimeLink toDomain(ExternalAnimeLinkDO data) {
        return new ExternalAnimeLink(
                data.id(),
                data.animeId(),
                data.externalType(),
                data.externalAnimeId(),
                SimpleEntityMetadataMapper.toDomain(data.metadata()));
    }

    public static ExternalAnimeLinkDO toData(ExternalAnimeLink data) {
        return new ExternalAnimeLinkDO(
                data.id(),
                data.animeId(),
                data.externalType(),
                data.externalAnimeId(),
                SimpleEntityMetadataMapper.toData(data.metadata()));
    }
}
