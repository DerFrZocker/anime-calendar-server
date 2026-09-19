package de.derfrzocker.anime.calendar.metadata.mongodb.mapper;

import de.derfrzocker.anime.calendar.metadata.AuditMetadata;
import de.derfrzocker.anime.calendar.metadata.mongodb.model.AuditMetadataDO;

public final class AuditMetadataMapper {

    private AuditMetadataMapper() {
    }

    public static AuditMetadata toDomain(AuditMetadataDO data) {
        return new AuditMetadata(data.createdAt(), data.createdBy(), data.lastModifiedAt(), data.lastModifiedBy());
    }

    public static AuditMetadataDO toData(AuditMetadata domain) {
        return new AuditMetadataDO(
                domain.createdAt(),
                domain.createdBy(),
                domain.lastModifiedAt(),
                domain.lastModifiedBy());
    }
}
