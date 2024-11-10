package de.derfrzocker.anime.calendar.server.mongodb.data;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import java.util.Set;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Calendar")
public class CalendarDO {

    @BsonId
    public CalendarId id;
    public CalendarKey key;
    public Instant createdAt;
    public UserId owner;
    public Set<AnimeOverrideDO> animeOverrides;
}
