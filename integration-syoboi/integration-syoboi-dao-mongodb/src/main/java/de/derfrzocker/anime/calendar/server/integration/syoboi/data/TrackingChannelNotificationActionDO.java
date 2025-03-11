package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "TrackingChannelNotificationAction")
public class TrackingChannelNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public TID tid;
    public String title;
    public Channel channel;
}
