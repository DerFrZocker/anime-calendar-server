package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import java.util.Optional;

public interface TIDDataProviderDao {

    Optional<ProvidedTIDData> provideById(TID id, RequestContext context);
}
