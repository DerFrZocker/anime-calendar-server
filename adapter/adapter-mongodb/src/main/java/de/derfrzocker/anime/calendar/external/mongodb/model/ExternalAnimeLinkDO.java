package de.derfrzocker.anime.calendar.external.mongodb.model;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeLinkId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.metadata.mongodb.model.SimpleEntityMetadataDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

@MongoEntity(collection = "external_anime_link")
public record ExternalAnimeLinkDO(
        @BsonId ExternalAnimeLinkId id,
        @BsonProperty("anime_id") AnimeId animeId,
        @BsonProperty("external_type") ExternalType externalType,
        @BsonProperty("external_anime_id") ExternalAnimeId externalAnimeId,
        @BsonProperty("metadata") SimpleEntityMetadataDO metadata) {

    public ExternalAnimeLinkDO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(animeId);
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
        Objects.requireNonNull(metadata);
    }
}
