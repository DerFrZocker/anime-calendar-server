package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.rest.handler.calendar.ICalCalendarRequestHandler;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@RequestScoped
public class SecuredICalCalendarRequestHandler {

    private static final UserId ICAL_USER = UserId.of("UICALHANDL");

    @Inject
    ICalCalendarRequestHandler requestHandler;

    public String getByKey(CalendarKey key) {
        return this.requestHandler.getByKey(key, createRequestContext());
    }

    private RequestContext createRequestContext() {
        return new RequestContext(ICAL_USER, Instant.now());
    }
}
