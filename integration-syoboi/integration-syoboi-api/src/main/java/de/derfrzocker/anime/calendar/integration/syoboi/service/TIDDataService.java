package de.derfrzocker.anime.calendar.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDDataUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface TIDDataService {

    Optional<TIDData> getById(TID id, RequestContext context);

    TIDData createWithData(TID id, TIDDataCreateData createData, RequestContext context);

    TIDData updateWithData(TID id, TIDDataUpdateData updateData, RequestContext context);

    void deleteById(TID id, RequestContext context);
}
