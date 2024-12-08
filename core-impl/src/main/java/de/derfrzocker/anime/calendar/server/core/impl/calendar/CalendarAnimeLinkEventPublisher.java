package de.derfrzocker.anime.calendar.server.core.impl.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PostCalendarAnimeLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PostCalendarAnimeLinkDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PostCalendarAnimeLinkUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkUpdateEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@RequestScoped
public class CalendarAnimeLinkEventPublisher {

    @Inject
    Event<PreCalendarAnimeLinkCreateEvent> preCreateEvent;
    @Inject
    Event<PostCalendarAnimeLinkCreateEvent> postCreateEvent;

    @Inject
    Event<PreCalendarAnimeLinkUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostCalendarAnimeLinkUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreCalendarAnimeLinkDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostCalendarAnimeLinkDeleteEvent> postDeleteEvent;

    public void firePreCreateEvent(CalendarId calendarId,
                                   AnimeId animeId,
                                   CalendarAnimeLinkCreateData createData,
                                   CalendarAnimeLink link,
                                   RequestContext context) {

        this.preCreateEvent.fire(new PreCalendarAnimeLinkCreateEvent(calendarId, animeId, createData, link, context));
    }

    public void firePostCreateEvent(CalendarId calendarId,
                                    AnimeId animeId,
                                    CalendarAnimeLinkCreateData createData,
                                    CalendarAnimeLink link,
                                    RequestContext context) {

        this.postCreateEvent.fire(new PostCalendarAnimeLinkCreateEvent(calendarId, animeId, createData, link, context));
    }

    public void firePreUpdateEvent(CalendarId calendarId,
                                   AnimeId animeId,
                                   CalendarAnimeLinkUpdateData updateData,
                                   CalendarAnimeLink current,
                                   CalendarAnimeLink updated,
                                   RequestContext context) {

        this.preUpdateEvent.fire(new PreCalendarAnimeLinkUpdateEvent(calendarId,
                                                                     animeId,
                                                                     updateData,
                                                                     current,
                                                                     updated,
                                                                     context));
    }

    public void firePostUpdateEvent(CalendarId calendarId,
                                    AnimeId animeId,
                                    CalendarAnimeLinkUpdateData updateData,
                                    CalendarAnimeLink current,
                                    CalendarAnimeLink updated,
                                    RequestContext context) {

        this.postUpdateEvent.fire(new PostCalendarAnimeLinkUpdateEvent(calendarId,
                                                                       animeId,
                                                                       updateData,
                                                                       current,
                                                                       updated,
                                                                       context));
    }

    public void firePreDeleteEvent(CalendarId calendarId,
                                   AnimeId animeId,
                                   CalendarAnimeLink link,
                                   RequestContext context) {

        this.preDeleteEvent.fire(new PreCalendarAnimeLinkDeleteEvent(calendarId, animeId, link, context));
    }

    public void firePostDeleteEvent(CalendarId calendarId,
                                    AnimeId animeId,
                                    CalendarAnimeLink link,
                                    RequestContext context) {

        this.postDeleteEvent.fire(new PostCalendarAnimeLinkDeleteEvent(calendarId, animeId, link, context));
    }
}
