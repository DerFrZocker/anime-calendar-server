package de.derfrzocker.anime.calendar.server.mongodb.dao.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarDao;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.CalendarData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.CalendarDomain;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;

@Dependent
public class CalendarMongoDBDaoImpl implements CalendarDao {

    @Inject
    CalendarMongoDBRepository repository;

    @Override
    public Optional<Calendar> getById(CalendarId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(CalendarData::toDomain);
    }

    @Override
    public void create(Calendar calendar, RequestContext context) {
        this.repository.persist(CalendarDomain.toData(calendar));
    }

    @Override
    public void update(Calendar calendar, RequestContext context) {
        this.repository.update(CalendarDomain.toData(calendar));
    }

    @Override
    public void delete(Calendar calendar, RequestContext context) {
        this.repository.deleteById(calendar.id());
    }
}
