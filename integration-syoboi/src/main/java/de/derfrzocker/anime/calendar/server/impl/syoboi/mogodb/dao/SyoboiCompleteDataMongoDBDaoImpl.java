package de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.dao;

import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data.SyoboiCompleteDataDO;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;

@Dependent
public class SyoboiCompleteDataMongoDBDaoImpl {

    @Inject
    SyoboiCompleteDataMongoDBRepository repository;

    public Optional<SyoboiCompleteDataDO> getById(TID tid, RequestContext context) {
        return this.repository.findByIdOptional(tid);
    }

    public void create(SyoboiCompleteDataDO data, RequestContext context) {
        this.repository.persist(data);
    }

    public void update(SyoboiCompleteDataDO data, RequestContext context) {
        this.repository.update(data);
    }

    public void delete(SyoboiCompleteDataDO data, RequestContext context) {
        this.repository.deleteById(data.tid);
    }
}
