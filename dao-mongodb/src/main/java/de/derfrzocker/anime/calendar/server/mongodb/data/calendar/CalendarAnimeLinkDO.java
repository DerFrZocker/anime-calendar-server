package de.derfrzocker.anime.calendar.server.mongodb.data.calendar;

import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "CalendarAnimeLink")
public class CalendarAnimeLinkDO extends ModificationInfoDO {

    @BsonId
    public ObjectId id;
    public CalendarId calendarId;
    public AnimeId animeId;
    public boolean include;
}
