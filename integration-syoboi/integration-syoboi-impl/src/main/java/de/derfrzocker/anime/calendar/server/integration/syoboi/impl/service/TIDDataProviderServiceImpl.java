package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.TIDDataProviderDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataProviderService;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class TIDDataProviderServiceImpl implements TIDDataProviderService {

    @Inject
    TIDDataProviderDao dao;

    @Override
    public Optional<ProvidedTIDData> provideById(TID id, RequestContext context) {
        return this.dao.provideById(id, context);
    }
}
