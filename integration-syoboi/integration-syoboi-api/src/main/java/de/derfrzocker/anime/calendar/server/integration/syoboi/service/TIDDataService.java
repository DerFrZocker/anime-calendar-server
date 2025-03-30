package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface TIDDataService {

    Stream<TIDData> getAll(RequestContext context);

    Optional<TIDData> getById(TID id, RequestContext context);

    TIDData createWithData(TID id, TIDDataCreateData createData, RequestContext context);

    TIDData updateWithData(TID id, TIDDataUpdateData updateData, RequestContext context);

    void deleteById(TID id, RequestContext context);
}
