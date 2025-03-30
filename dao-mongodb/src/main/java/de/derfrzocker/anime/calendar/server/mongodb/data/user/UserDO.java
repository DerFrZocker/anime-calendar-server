package de.derfrzocker.anime.calendar.server.mongodb.data.user;

import de.derfrzocker.anime.calendar.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.Set;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "User")
public class UserDO extends ModificationInfoDO {

    @BsonId
    public UserId id;
    public HashedUserToken hashedToken;
    public Set<CalendarId> calendars;
    public Set<AnimeAccountLinkId> animeAccountLinks;
}
