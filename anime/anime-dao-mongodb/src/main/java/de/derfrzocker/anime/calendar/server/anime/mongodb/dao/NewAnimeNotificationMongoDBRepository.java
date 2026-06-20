package de.derfrzocker.anime.calendar.server.anime.mongodb.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.NewAnimeNotificationDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NewAnimeNotificationMongoDBRepository
        implements PanacheMongoRepositoryBase<NewAnimeNotificationDO, NotificationId> {

}
