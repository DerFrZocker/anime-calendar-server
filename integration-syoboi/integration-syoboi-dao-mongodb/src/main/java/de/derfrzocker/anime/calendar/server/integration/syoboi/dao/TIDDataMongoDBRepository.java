package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TIDDataDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TIDDataMongoDBRepository implements PanacheMongoRepositoryBase<TIDDataDO, TID> {

}
