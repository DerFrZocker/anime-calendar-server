package de.derfrzocker.anime.calendar.server.mongodb.dao.calendar;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarAnimeLinkDao;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.mongodb.data.calendar.CalendarAnimeLinkDO;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.CalendarAnimeLinkData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.CalendarAnimeLinkDomain;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class CalendarAnimeLinkMongoDBDaoImpl implements CalendarAnimeLinkDao {

    @Inject
    CalendarAnimeLinkMongoDBRepository repository;

    @Override
    public Stream<CalendarAnimeLink> getAllWithId(CalendarId calendarId, RequestContext context) {
        return this.repository.find("calendarId", calendarId.raw()).stream().map(CalendarAnimeLinkData::toDomain);
    }

    @Override
    public Optional<CalendarAnimeLink> getById(CalendarId calendarId, AnimeId animeId, RequestContext context) {
        return findById(calendarId, animeId).map(CalendarAnimeLinkData::toDomain);
    }

    @Override
    public void create(CalendarAnimeLink calendarAnimeLink, RequestContext context) {
        this.repository.persist(CalendarAnimeLinkDomain.toData(calendarAnimeLink));
    }

    @Override
    public void update(CalendarAnimeLink calendarAnimeLink, RequestContext context) {
        findById(calendarAnimeLink.calendarId(), calendarAnimeLink.animeId()).ifPresent(data -> {
            CalendarAnimeLinkDO newData = CalendarAnimeLinkDomain.toData(calendarAnimeLink);
            newData.id = data.id;
            this.repository.update(newData);
        });
    }

    @Override
    public void delete(CalendarAnimeLink calendarAnimeLink, RequestContext context) {
        this.repository.delete("calendarId = ?1 and animeId = ?2",
                               calendarAnimeLink.calendarId().raw(),
                               calendarAnimeLink.animeId().raw());
    }

    private Optional<CalendarAnimeLinkDO> findById(CalendarId calendarId, AnimeId animeId) {
        return this.repository.find("calendarId = ?1 and animeId = ?2", calendarId.raw(), animeId.raw())
                              .firstResultOptional();
    }
}
