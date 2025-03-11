package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;

public interface TIDDataProviderService {

    Optional<ProvidedTIDData> provideById(TID id, RequestContext context);
}
