package de.derfrzocker.anime.calendar.server.mongodb.data.permission;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.mongodb.data.ModificationInfoDO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "UserPermission")
public class UserPermissionDO extends ModificationInfoDO {

    @BsonId
    public UserId id;
    public ObjectPermissionDO user;
    public ObjectPermissionDO calendar;
    public ObjectPermissionDO anime;
    public ObjectPermissionDO calendarAnimeLink;
}
