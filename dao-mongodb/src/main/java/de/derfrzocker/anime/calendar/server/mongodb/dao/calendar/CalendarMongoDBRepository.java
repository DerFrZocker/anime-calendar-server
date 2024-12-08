package de.derfrzocker.anime.calendar.server.mongodb.dao.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalendarMongoDBRepository implements PanacheMongoRepositoryBase<CalendarDO, CalendarId> {

}
