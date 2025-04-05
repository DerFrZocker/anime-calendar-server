package de.derfrzocker.anime.calendar.server.anime.mongodb.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.NewAnimeNotificationActionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NewAnimeNotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<NewAnimeNotificationActionDO, NotificationActionId> {

}
