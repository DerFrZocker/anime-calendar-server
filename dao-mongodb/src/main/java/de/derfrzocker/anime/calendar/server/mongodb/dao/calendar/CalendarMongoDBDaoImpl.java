package de.derfrzocker.anime.calendar.server.mongodb.dao.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarDao;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.CalendarDO;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.Data;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.Domain;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Optional;

@RequestScoped
public class CalendarMongoDBDaoImpl implements CalendarDao {

    @Inject
    CalendarMongoDBRepository repository;

    @Override
    public Optional<Calendar> getById(CalendarId id) {
        return Optional.ofNullable(repository.findById(id)).map(Data::toDomain);
    }

    @Override
    public Calendar createWithData(CalendarCreateData calendarCreateData) {
        CalendarDO calendarDao = Domain.toData(calendarCreateData);

        calendarDao.createdAt = Instant.now();

        repository.persist(calendarDao);

        return Data.toDomain(calendarDao);
    }

    @Override
    public Calendar updateWithChangeData(Calendar calendar, CalendarChangeData calendarChangeData) {
        CalendarDO calendarDO = Domain.toData(calendar, calendarChangeData);

        repository.update(calendarDO);

        return Data.toDomain(calendarDO);
    }
}
