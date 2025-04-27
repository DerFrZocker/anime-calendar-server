package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.data;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "StreamingNotification")
public class StreamingNotificationDO extends ModificationInfoDO {

    @BsonId
    public NotificationId id;
    public AnimeId animeId;
    public int orgEpisodeIndex;
    public Instant orgStreamingTime;
    public IntegrationId referenceIntegrationId;
    public IntegrationAnimeId referenceIntegrationAnimeId;
}
