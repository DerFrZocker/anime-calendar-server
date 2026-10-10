package de.derfrzocker.anime.calendar.anime.mongodb.adapter;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.mongodb.mapper.AnimeMapper;
import de.derfrzocker.anime.calendar.anime.out.ForManagingAnime;
import de.derfrzocker.anime.calendar.mongodb.mapper.BsonJakartaJsonMapper;
import de.derfrzocker.anime.calendar.mongodb.mapper.JakartaJsonBsonMapper;
import io.smallrye.common.annotation.Identifier;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.function.Function;
import org.bson.BsonDocument;

@ApplicationScoped
class AnimeRepository implements ForManagingAnime {

    private static final int BATCH_SIZE = 1000;

    private final AnimePanache panache;
    private final Instance<LayerConfigCodec<?>> layerConfigCodecs;

    @Inject
    AnimeRepository(AnimePanache panache, @Any Instance<LayerConfigCodec<?>> layerConfigCodecs) {
        this.panache = panache;
        this.layerConfigCodecs = layerConfigCodecs;
    }

    @Override
    public Uni<Optional<Anime>> retrieve(AnimeId id) {
        return panache.findByIdOptional(id).map(opt -> opt.map(data -> AnimeMapper.toDomain(data, this::toDomain)));
    }

    @Override
    public Multi<Anime> retrieveAll(Multi<AnimeId> ids) {
        return ids
                .group()
                .intoLists()
                .of(BATCH_SIZE)
                .onItem()
                .transformToUni(panache::findByIds)
                .concatenate()
                .onItem()
                .transformToIterable(Function.identity())
                .map(data -> AnimeMapper.toDomain(data, this::toDomain));
    }

    @Override
    public Uni<Void> store(Anime anime) {
        return panache.persistOrUpdate(AnimeMapper.toData(anime, this::toData)).replaceWithVoid();
    }

    private LayerConfigData toDomain(LayerConfigDataType type, BsonDocument data) {
        return codec(type).deserialize(BsonJakartaJsonMapper.toJson(data));
    }

    private <T extends LayerConfigData> BsonDocument toData(LayerConfigDataType type, T data) {
        return JakartaJsonBsonMapper.toBson(codec(type).serialize(data));
    }

    @SuppressWarnings("unchecked")
    private <T extends LayerConfigData> LayerConfigCodec<T> codec(LayerConfigDataType type) {
        return (LayerConfigCodec<T>) layerConfigCodecs.select(Identifier.Literal.of(type.raw())).get();
    }
}
