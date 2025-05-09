package de.derfrzocker.anime.calendar.server.anime.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.anime.mongodb.mapper.AnimeDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.dao.AnimeDao;
import de.derfrzocker.anime.calendar.server.anime.mongodb.mapper.AnimeDataMapper;
import de.derfrzocker.anime.calendar.server.layer.service.LayerConfigParserService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class AnimeMongoDBDaoImpl implements AnimeDao {

    @Inject
    AnimeMongoDBRepository repository;
    @Inject
    LayerConfigParserService configParserService;

    @Override
    public Stream<Anime> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(data -> AnimeDataMapper.toDomain(data, this.configParserService));
    }

    @Override
    public Stream<Anime> getAllByIds(Collection<AnimeId> ids, RequestContext context) {
        return this.repository.find("_id in ?1", ids.stream().map(AnimeId::raw).toList())
                              .stream()
                              .map(data -> AnimeDataMapper.toDomain(data, this.configParserService));
    }

    @Override
    public Optional<Anime> getById(AnimeId id, RequestContext context) {
        return this.repository.findByIdOptional(id)
                              .map(data -> AnimeDataMapper.toDomain(data, this.configParserService));
    }

    @Override
    public void create(Anime anime, RequestContext context) {
        this.repository.persist(toData(anime, this.configParserService));
    }

    @Override
    public void update(Anime anime, RequestContext context) {
        this.repository.update(toData(anime, this.configParserService));
    }

    @Override
    public void delete(Anime anime, RequestContext context) {
        this.repository.deleteById(anime.id());
    }
}
