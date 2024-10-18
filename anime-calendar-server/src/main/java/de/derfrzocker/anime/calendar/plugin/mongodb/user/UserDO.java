package de.derfrzocker.anime.calendar.plugin.mongodb.user;

import de.derfrzocker.anime.calendar.api.user.UserId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import java.util.Set;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "User")
public class UserDO {

    @BsonId
    public UserId userId;
    public Instant createdAt;
    public Set<String> calendars;
    public Set<String> animeAccountLinks;
}
