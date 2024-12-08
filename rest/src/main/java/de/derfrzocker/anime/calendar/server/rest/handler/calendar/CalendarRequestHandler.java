package de.derfrzocker.anime.calendar.server.rest.handler.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.CalendarDomain;
import de.derfrzocker.anime.calendar.server.rest.mapper.transfer.CalendarTransfer;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CalendarRequestHandler {

    @Inject
    CalendarService service;

    public CalendarResponse getById(CalendarId id, RequestContext context) {
        return this.service.getById(id, context).map(this::toResponse).orElseThrow(ResourceNotFoundException.with(id));
    }

    public CalendarResponse createWithData(CalendarCreateRequest request, RequestContext context) {
        return toResponse(this.service.createWithData(CalendarTransfer.toDomain(request.calendar()), context));
    }

    private CalendarResponse toResponse(Calendar domain) {
        return new CalendarResponse(CalendarDomain.toTransfer(domain));
    }
}
