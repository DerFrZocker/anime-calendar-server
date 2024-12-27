package de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.dao;

import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data.SyoboiIncompleteDataDO;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;

@Dependent
public class SyoboiIncompleteDataMongoDBDaoImpl {

    @Inject
    SyoboiIncompleteDataMongoDBRepository repository;

    public Optional<SyoboiIncompleteDataDO> getById(TID tid, RequestContext context) {
        return this.repository.findByIdOptional(tid);
    }

    public void create(SyoboiIncompleteDataDO data, RequestContext context) {
        this.repository.persist(data);
    }

    public void update(SyoboiIncompleteDataDO data, RequestContext context) {
        this.repository.update(data);
    }

    public void delete(SyoboiIncompleteDataDO data, RequestContext context) {
        this.repository.deleteById(data.tid);
    }
}
