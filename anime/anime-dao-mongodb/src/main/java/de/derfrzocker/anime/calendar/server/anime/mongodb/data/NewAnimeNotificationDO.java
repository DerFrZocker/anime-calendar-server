package de.derfrzocker.anime.calendar.server.anime.mongodb.data;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection = "new_anime_notification")
public record NewAnimeNotificationDO(
        @BsonId NotificationId id,
        @BsonProperty("integration_id") IntegrationId integrationId,
        @BsonProperty("integration_anime_id") IntegrationAnimeId integrationAnimeId) {

}
