package de.derfrzocker.anime.calendar.server.core.impl.calendar;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PostCalendarCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarCreateEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@RequestScoped
public class CalendarEventPublisher {

    @Inject
    Event<PreCalendarCreateEvent> preCreateEvent;
    @Inject
    Event<PostCalendarCreateEvent> postCreateEvent;

    public void firePreCreateEvent(CalendarCreateData createData, Calendar calendar, RequestContext context) {

        this.preCreateEvent.fire(new PreCalendarCreateEvent(createData, calendar, context));
    }

    public void firePostCreateEvent(CalendarCreateData createData,
                                    Calendar calendar,
                                    RequestContext context) {

        this.postCreateEvent.fire(new PostCalendarCreateEvent(createData, calendar, context));
    }
}