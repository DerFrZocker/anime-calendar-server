package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.TIDDataDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.TIDDataDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class TIDDataMongoDBDaoImpl implements TIDDataDao {

    @Inject
    TIDDataMongoDBRepository repository;

    @Override
    public Stream<TIDData> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(TIDDataDataMapper::toDomain);
    }

    @Override
    public Optional<TIDData> getById(TID id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(TIDDataDataMapper::toDomain);
    }

    @Override
    public void create(TIDData tidData, RequestContext context) {
        this.repository.persist(toData(tidData));
    }

    @Override
    public void update(TIDData tidData, RequestContext context) {
        this.repository.update(toData(tidData));
    }

    @Override
    public void delete(TIDData tidData, RequestContext context) {
        this.repository.deleteById(tidData.tid());
    }
}
