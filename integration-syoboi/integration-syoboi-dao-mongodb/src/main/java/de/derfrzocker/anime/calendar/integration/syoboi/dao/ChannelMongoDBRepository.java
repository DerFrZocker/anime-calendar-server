package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.integration.syoboi.data.ChannelDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChannelMongoDBRepository implements PanacheMongoRepositoryBase<ChannelDO, ChannelId> {

}
