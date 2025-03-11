package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;
import java.util.stream.Stream;

public interface TIDDataDao {

    Stream<TIDData> getAll(RequestContext context);

    Optional<TIDData> getById(TID id, RequestContext context);

    void create(TIDData tidData, RequestContext context);

    void update(TIDData tidData, RequestContext context);

    void delete(TIDData tidData, RequestContext context);
}
