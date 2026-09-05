package de.derfrzocker.anime.calendar.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import java.util.Optional;

public interface TIDDataProviderService {

    Optional<ProvidedTIDData> provideById(TID id, RequestContext context);
}
