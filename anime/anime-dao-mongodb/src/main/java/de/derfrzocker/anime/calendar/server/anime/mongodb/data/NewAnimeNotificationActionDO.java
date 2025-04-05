package de.derfrzocker.anime.calendar.server.anime.mongodb.data;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.Map;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "NewAnimeNotificationAction")
public class NewAnimeNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public String title;
    public int episodeCount;
    public int score;
    public Map<String, String> links;
}
