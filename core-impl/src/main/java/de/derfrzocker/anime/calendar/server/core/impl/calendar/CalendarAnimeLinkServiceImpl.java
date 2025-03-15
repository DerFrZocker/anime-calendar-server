package de.derfrzocker.anime.calendar.server.core.impl.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarAnimeLinkDao;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarAnimeLinkService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class CalendarAnimeLinkServiceImpl implements CalendarAnimeLinkService {

    @Inject
    CalendarAnimeLinkDao dao;
    @Inject
    CalendarAnimeLinkEventPublisher eventPublisher;

    @Override
    public Stream<CalendarAnimeLink> getAllWithId(CalendarId calendarId, RequestContext context) {
        return this.dao.getAllWithId(calendarId, context);
    }

    @Override
    public Optional<CalendarAnimeLink> getById(CalendarId calendarId, AnimeId animeId, RequestContext context) {
        return this.dao.getById(calendarId, animeId, context);
    }

    @Override
    public CalendarAnimeLink createWithData(CalendarId calendarId,
                                            AnimeId animeId,
                                            CalendarAnimeLinkCreateData createData,
                                            RequestContext context) {

        Optional<CalendarAnimeLink> optional = getById(calendarId, animeId, context);
        if (optional.isPresent()) {
            // TODO 2024-12-07: Better exception
            throw new RuntimeException();
        }

        CalendarAnimeLink link = CalendarAnimeLink.from(calendarId, animeId, createData, context);

        this.eventPublisher.firePreCreateEvent(calendarId, animeId, createData, link, context);
        this.dao.create(link, context);
        this.eventPublisher.firePostCreateEvent(calendarId, animeId, createData, link, context);

        return link;
    }

    @Override
    public CalendarAnimeLink updateWithData(CalendarId calendarId,
                                            AnimeId animeId,
                                            CalendarAnimeLinkUpdateData updateData,
                                            RequestContext context) {

        CalendarAnimeLink current = getById(calendarId, animeId, context).orElseThrow(ResourceNotFoundException.with(
                calendarId,
                animeId));
        CalendarAnimeLink updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdateEvent(calendarId, animeId, updateData, current, updated, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdateEvent(calendarId, animeId, updateData, current, updated, context);

        return updated;
    }

    @Override
    public void deleteById(CalendarId calendarId, AnimeId animeId, RequestContext context) {
        CalendarAnimeLink link = getById(calendarId, animeId, context).orElseThrow(ResourceNotFoundException.with(
                calendarId,
                animeId));

        this.eventPublisher.firePreDeleteEvent(calendarId, animeId, link, context);
        this.dao.delete(link, context);
        this.eventPublisher.firePostDeleteEvent(calendarId, animeId, link, context);
    }
}
