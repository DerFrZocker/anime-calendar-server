package de.derfrzocker.anime.calendar.server.integration.mongodb.data;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.jetbrains.annotations.Nullable;

@MongoEntity(collection = "ManualLinkNotificationAction")
public class ManualLinkNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public AnimeId animeId;
    public IntegrationId integrationId;
    @Nullable
    public IntegrationAnimeId integrationAnimeId;
}
