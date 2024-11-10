package de.derfrzocker.anime.calendar.server.mongodb.data;

import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;
import java.util.Set;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "User")
public class UserDO {

    @BsonId
    public UserId id;
    public Instant createdAt;
    public HashedUserToken hashedToken;
    public Set<CalendarId> calendars;
    public Set<AnimeAccountLinkId> animeAccountLinks;
}
