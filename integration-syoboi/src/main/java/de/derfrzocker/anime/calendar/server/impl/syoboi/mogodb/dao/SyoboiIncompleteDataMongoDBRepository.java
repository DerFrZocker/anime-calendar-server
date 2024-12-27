package de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.dao;

import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data.SyoboiIncompleteDataDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SyoboiIncompleteDataMongoDBRepository implements PanacheMongoRepositoryBase<SyoboiIncompleteDataDO, TID> {

}
