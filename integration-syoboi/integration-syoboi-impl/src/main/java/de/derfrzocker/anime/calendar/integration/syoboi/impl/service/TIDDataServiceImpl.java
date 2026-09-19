package de.derfrzocker.anime.calendar.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.integration.syoboi.dao.TIDDataDao;
import de.derfrzocker.anime.calendar.integration.syoboi.service.TIDDataService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.exception.TIDDataExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.integration.syoboi.exception.TIDDataExceptions.notFound;

@ApplicationScoped
public class TIDDataServiceImpl implements TIDDataService {

    @Inject
    TIDDataDao dao;
    @Inject
    TIDDataEventPublisher eventPublisher;

    @Override
    public Optional<TIDData> getById(TID id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public TIDData createWithData(TID id, TIDDataCreateData createData, RequestContext context) {
        TIDData tidData = TIDData.from(id, createData, context);

        this.dao.create(tidData, context);
        this.eventPublisher.firePostCreate(tidData, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public TIDData updateWithData(TID id, TIDDataUpdateData updateData, RequestContext context) {
        TIDData current = getById(id, context).orElseThrow(notFound(id));
        TIDData updated = current.updateWithData(updateData, context);

        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(TID id, RequestContext context) {
        TIDData tidData = getById(id, context).orElseThrow(notFound(id));

        this.dao.delete(tidData, context);
    }
}
