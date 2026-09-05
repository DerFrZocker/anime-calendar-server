package de.derfrzocker.anime.calendar.integration.syoboi.data;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "TrackingChannelNotificationAction")
public class TrackingChannelNotificationActionDO extends ModificationInfoDO {

    @BsonId
    public NotificationActionId id;
    public TID tid;
    public ChannelId channelId;
    public String channelName;
}
