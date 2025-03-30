package de.derfrzocker.anime.calendar.server.notify.mongodb.data;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "NotificationAction")
public class NotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public NotificationId notificationId;
    public NotificationActionType actionType;
    public Instant executedAt;
    public UserId executedBy;
}
