package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;

public interface ResolvedTIDDataService {

    Optional<ResolvedTIDData> resolveById(TID id, RequestContext context);
}
