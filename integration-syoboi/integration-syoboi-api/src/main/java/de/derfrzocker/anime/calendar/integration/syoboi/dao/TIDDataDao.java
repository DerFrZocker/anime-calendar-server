package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDData;
import java.util.Optional;
import java.util.stream.Stream;

public interface TIDDataDao {

    Optional<TIDData> getById(TID id, RequestContext context);

    void create(TIDData tidData, RequestContext context);

    void update(TIDData tidData, RequestContext context);

    void delete(TIDData tidData, RequestContext context);
}
