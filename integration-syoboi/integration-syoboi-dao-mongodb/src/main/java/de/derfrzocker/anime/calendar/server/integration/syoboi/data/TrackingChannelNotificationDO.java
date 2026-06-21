package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.jspecify.annotations.Nullable;

@MongoEntity(collection = "tracking_channel_notification")
public record TrackingChannelNotificationDO(
        @BsonId NotificationId id,
        @BsonProperty("tid") TID tid,
        @BsonProperty("title") String title,
        @BsonProperty("current_channel_id") @Nullable ChannelId currentChannelId,
        @BsonProperty("current_channel_name") @Nullable String currentChannelName) {

}
