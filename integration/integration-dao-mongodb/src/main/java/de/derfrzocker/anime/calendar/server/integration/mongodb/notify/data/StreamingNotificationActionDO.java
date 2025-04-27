package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.data;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import org.bson.codecs.pojo.annotations.BsonId;
import org.jetbrains.annotations.Nullable;

@MongoEntity(collection = "StreamingNotificationAction")
public class StreamingNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public AnimeId animeId;
    public int orgEpisodeIndex;
    public @Nullable IntegrationId integrationId;
    public @Nullable IntegrationAnimeId integrationAnimeId;
    public int streamingEpisode;
    public @Nullable Instant streamingTime;
}
