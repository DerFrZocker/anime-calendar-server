package de.derfrzocker.anime.calendar.server.notify.mongodb.data;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Notification")
public class NotificationDO extends ModificationInfoDO {

    @BsonId
    public NotificationId id;
    public NotificationType type;
    public Instant validUntil;
}
