package de.derfrzocker.anime.calendar.server.rest.handler.calendar;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarAnimeLinkService;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class ICalCalendarRequestHandler {

    @Inject
    ICalCalendarService iCalService;
    @Inject
    CalendarService calendarService;
    @Inject
    CalendarAnimeLinkService calendarLinkService;

    public String getByKey(CalendarKey key, RequestContext context) {
        Calendar calendar = this.calendarService.getById(key.calendarId(), context)
                                                .orElseThrow(ResourceNotFoundException.with(key.calendarId()));

        if (!key.equals(calendar.key())) {
            throw ResourceNotFoundException.with(key.calendarId()).get();
        }

        Set<AnimeId> ids = this.calendarLinkService.getAllWithId(calendar.id(), context)
                                                   .filter(CalendarAnimeLink::include)
                                                   .map(CalendarAnimeLink::animeId)
                                                   .collect(Collectors.toSet());

        // TODO 2024-12-16: Make options an rest argument
        return this.iCalService.build(ids, AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE).build(), context).raw();
    }
}
