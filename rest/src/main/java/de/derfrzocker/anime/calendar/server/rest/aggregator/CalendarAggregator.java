package de.derfrzocker.anime.calendar.server.rest.aggregator;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.exception.CalendarNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.Domain;
import de.derfrzocker.anime.calendar.server.rest.mapper.transfer.Transfer;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.AnimeOverrideCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CalendarAggregator {

    @Inject
    CalendarService calendarService;
    @Inject
    UserSecurityProvider userSecurityProvider;

    public CalendarResponse getById(CalendarId id) throws CalendarNotFoundException, UnauthenticatedException {
        Calendar calendar = calendarService.getById(id).orElseThrow(CalendarNotFoundException.withId(id));

        userSecurityProvider.ensureAccessToUserData(calendar.owner());

        return new CalendarResponse(Domain.toTransfer(calendar));
    }

    public CalendarCreateResponse create() throws UnauthenticatedException {
        UserId currentUser = userSecurityProvider.getCurrentUserId();

        Calendar calendar = calendarService.createWithUser(currentUser);

        return new CalendarCreateResponse(Domain.toTransfer(calendar));
    }

    public CalendarResponse createAnimeOverride(CalendarId id, AnimeOverrideCreateRequest createRequest) throws
                                                                                                         CalendarNotFoundException,
                                                                                                         UnauthenticatedException {
        userSecurityProvider.ensureAccessToUserData(calendarService.getById(id)
                                                                   .map(Calendar::owner)
                                                                   .orElseThrow(CalendarNotFoundException.withId(id)));

        return new CalendarResponse(Domain.toTransfer(calendarService.createAnimeOverride(id,
                                                                                          Transfer.toDomain(
                                                                                                  createRequest.animeOverrideCreateData()))));
    }
}
