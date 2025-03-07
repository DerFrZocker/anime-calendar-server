package de.derfrzocker.anime.calendar.server.integration.mongodb.data;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Notification")
public class IntegrationLinkNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public AnimeId animeId;
    public IntegrationId integrationId;
    public IntegrationAnimeId integrationAnimeId;
    public int score;
    public String bestName;
}
