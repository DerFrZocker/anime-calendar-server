package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.integration.syoboi.mapper.TIDDataDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.mapper.TIDDataDataMapper.toData;

@Dependent
public class TIDDataMongoDBDaoImpl implements TIDDataDao {

    @Inject
    TIDDataMongoDBRepository repository;

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
