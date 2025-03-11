package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;

public interface TIDDataProviderDao {

    Optional<ProvidedTIDData> provideById(TID id, RequestContext context);
}
