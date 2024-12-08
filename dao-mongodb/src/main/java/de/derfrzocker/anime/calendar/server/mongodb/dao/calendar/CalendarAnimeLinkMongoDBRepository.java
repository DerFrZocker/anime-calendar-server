package de.derfrzocker.anime.calendar.server.mongodb.dao.calendar;

import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarAnimeLinkDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalendarAnimeLinkMongoDBRepository implements PanacheMongoRepository<CalendarAnimeLinkDO> {

}
