package de.derfrzocker.anime.calendar.server.mongodb.data.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Calendar")
public class CalendarDO extends ModificationInfoDO {

    @BsonId
    public CalendarId id;
    public CalendarKey key;
    public UserId owner;
    public String name;
}
