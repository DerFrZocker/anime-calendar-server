/*
 * MIT License
 *
 * Copyright (c) 2022 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.derfrzocker.anime.calendar.web.ressource;

import de.derfrzocker.anime.calendar.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.api.user.UserService;
import de.derfrzocker.anime.calendar.integration.Integrations;
import de.derfrzocker.anime.calendar.web.constrain.ValidateCalendarKey;
import jakarta.annotation.security.DenyAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Set;

@Path("ical")
@RequestScoped
@DenyAll
public class ICalRessource {

    @Inject
    UserService userService;

    @Inject
    CalendarService calendarService;

    @Inject
    IntegrationService integrationService;

    @GET
    @Path("myanimelist/{userId}")
    @Produces("text/calendar")
    public Response getMyAnimeList(@PathParam("userId") IntegrationUserId userId) {
        return Response.ok(integrationService.getCalendar(Integrations.MY_ANIME_LIST_ID, userId).toString()).build();
    }

    @GET
    @Path("myanimelist")
    @Produces("text/calendar")
    public Response getMyAnimeList(@QueryParam("ani") Set<IntegrationAnimeId> queryAnime) {
        return Response.ok(integrationService.getCalendar(Integrations.MY_ANIME_LIST_ID, queryAnime).toString()).build();
    }

    @GET
    @Path("{calendarKey}")
    @Produces("text/calendar")
    public Response getPersonal(@ValidateCalendarKey @PathParam("calendarKey") CalendarKey calendarKey) {
        return Response.ok(calendarService.buildCalendar(null, AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE).build()).toString()).build();
    }
}
