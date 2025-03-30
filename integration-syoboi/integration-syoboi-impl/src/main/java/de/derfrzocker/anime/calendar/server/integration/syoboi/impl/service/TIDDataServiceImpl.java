package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TIDDataExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TIDDataExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.TIDDataDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class TIDDataServiceImpl implements TIDDataService {

    @Inject
    TIDDataDao dao;
    @Inject
    TIDDataEventPublisher eventPublisher;

    @Override
    public Stream<TIDData> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<TIDData> getById(TID id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public TIDData createWithData(TID id, TIDDataCreateData createData, RequestContext context) {
        TIDData tidData = TIDData.from(id, createData, context);

        this.eventPublisher.firePreCreate(tidData, createData, context);
        this.dao.create(tidData, context);
        this.eventPublisher.firePostCreate(tidData, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public TIDData updateWithData(TID id, TIDDataUpdateData updateData, RequestContext context) {
        TIDData current = getById(id, context).orElseThrow(notFound(id));
        TIDData updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(TID id, RequestContext context) {
        TIDData tidData = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(tidData, context);
        this.dao.delete(tidData, context);
        this.eventPublisher.firePostDelete(tidData, context);
    }
}
