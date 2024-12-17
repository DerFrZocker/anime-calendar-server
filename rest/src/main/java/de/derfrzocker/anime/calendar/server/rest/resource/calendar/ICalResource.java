package de.derfrzocker.anime.calendar.server.rest.resource.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateCalendarKey;
import de.derfrzocker.anime.calendar.server.rest.security.SecuredICalCalendarRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.Set;

@Path("v3/ical")
@Produces("text/calendar")
@RequestScoped
public class ICalResource {

    @Inject
    SecuredICalCalendarRequestHandler requestHandler;

    @GET
    @Path("{calendarKey}")
    @PermitAll
    public String getByKey(@ValidateCalendarKey @PathParam("calendarKey") CalendarKey key) {
        return this.requestHandler.getByKey(key);
    }

    @GET
    @Path("myanimelist")
    // TODO 2024-12-16: PermitAll and validation
    public String getByMyAnimeListId(@QueryParam("ani") Set<IntegrationAnimeId> ids) {
        return this.requestHandler.getByMyAnimeListId(ids);
    }
}
