package de.derfrzocker.anime.calendar.integration.syoboi.data;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "IgnoreTIDDataNotificationAction")
public class IgnoreTIDDataNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public TID tid;
}
