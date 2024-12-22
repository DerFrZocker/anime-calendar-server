package de.derfrzocker.anime.calendar.server.mongodb.dao.anime;

import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeDao;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.AnimeData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.AnimeDomain;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;

@Dependent
public class AnimeMongoDBDaoImpl implements AnimeDao {

    @Inject
    AnimeMongoDBRepository repository;
    @Inject
    LayerParser layerParser;

    @Override
    public Optional<Anime> getById(AnimeId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(data -> AnimeData.toDomain(data, this.layerParser));
    }

    @Override
    public void create(Anime anime, RequestContext context) {
        this.repository.persist(AnimeDomain.toData(anime, this.layerParser));
    }

    @Override
    public void update(Anime anime, RequestContext context) {
        this.repository.update(AnimeDomain.toData(anime, this.layerParser));
    }

    @Override
    public void delete(Anime anime, RequestContext context) {
        this.repository.deleteById(anime.id());
    }
}
