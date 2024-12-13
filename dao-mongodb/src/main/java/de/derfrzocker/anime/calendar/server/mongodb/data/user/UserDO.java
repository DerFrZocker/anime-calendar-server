package de.derfrzocker.anime.calendar.server.mongodb.data.user;

import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import de.derfrzocker.anime.calendar.server.mongodb.data.ModificationInfoDO;
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
