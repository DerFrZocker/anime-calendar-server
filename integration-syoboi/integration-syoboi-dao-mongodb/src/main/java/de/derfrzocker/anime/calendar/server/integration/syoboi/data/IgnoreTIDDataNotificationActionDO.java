package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "IgnoreTIDDataNotificationAction")
public class IgnoreTIDDataNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public TID tid;
}
