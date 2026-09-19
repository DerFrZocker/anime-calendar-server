package de.derfrzocker.anime.calendar.metadata.mongodb.mapper;

import de.derfrzocker.anime.calendar.metadata.SimpleEntityMetadata;
import de.derfrzocker.anime.calendar.metadata.mongodb.model.SimpleEntityMetadataDO;

public final class SimpleEntityMetadataMapper {

    private SimpleEntityMetadataMapper() {
    }

    public static SimpleEntityMetadata toDomain(SimpleEntityMetadataDO data) {
        return new SimpleEntityMetadata(AuditMetadataMapper.toDomain(data.audit()));
    }

    public static SimpleEntityMetadataDO toData(SimpleEntityMetadata domain) {
        return new SimpleEntityMetadataDO(AuditMetadataMapper.toData(domain.audit()));
    }
}
