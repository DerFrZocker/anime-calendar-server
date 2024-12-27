package de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.dao;

import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data.SyoboiCompleteDataDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SyoboiCompleteDataMongoDBRepository implements PanacheMongoRepositoryBase<SyoboiCompleteDataDO, TID> {

}
