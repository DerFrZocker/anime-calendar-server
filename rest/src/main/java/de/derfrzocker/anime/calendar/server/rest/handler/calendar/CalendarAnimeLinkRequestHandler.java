package de.derfrzocker.anime.calendar.server.rest.handler.calendar;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarAnimeLinkService;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkUpdateData;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.CalendarAnimeLinkDomain;
import de.derfrzocker.anime.calendar.server.rest.mapper.transfer.CalendarAnimeLinkTransfer;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarAnimeLinkCreateOrUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkListResponse;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkResponse;
import de.derfrzocker.anime.calendar.server.rest.util.Response;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CalendarAnimeLinkRequestHandler {

    @Inject
    CalendarAnimeLinkService service;

    public CalendarAnimeLinkListResponse getAllWithId(CalendarId calendarId, RequestContext context) {
        return this.service.getAllWithId(calendarId, context)
                           .map(CalendarAnimeLinkDomain::toTransfer)
                           .collect(Response.ofType(CalendarAnimeLinkListResponse::new));
    }

    public CalendarAnimeLinkResponse createOrUpdateWithData(CalendarId calendarId,
                                                            AnimeId animeId,
                                                            CalendarAnimeLinkCreateOrUpdateRequest request,
                                                            RequestContext context) {

        if (this.service.getById(calendarId, animeId, context).isPresent()) {
            CalendarAnimeLinkUpdateData data = CalendarAnimeLinkTransfer.toUpdateData(request.calendarAnimeLink());
            return toResponse(this.service.updateWithData(calendarId, animeId, data, context));
        } else {
            CalendarAnimeLinkCreateData data = CalendarAnimeLinkTransfer.toCreateData(request.calendarAnimeLink());
            return toResponse(this.service.createWithData(calendarId, animeId, data, context));
        }
    }

    public void deleteById(CalendarId calendarId, AnimeId animeId, RequestContext context) {
        this.service.deleteById(calendarId, animeId, context);
    }

    private CalendarAnimeLinkResponse toResponse(CalendarAnimeLink domain) {
        return new CalendarAnimeLinkResponse(CalendarAnimeLinkDomain.toTransfer(domain));
    }
}
