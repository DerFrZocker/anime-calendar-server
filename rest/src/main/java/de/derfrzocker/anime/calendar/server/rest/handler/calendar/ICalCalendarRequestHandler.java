package de.derfrzocker.anime.calendar.server.rest.handler.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarAnimeLinkService;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.core.api.integration.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.ical.Region;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class ICalCalendarRequestHandler {

    private static final IntegrationId MY_ANIME_LIST_INTEGRATION = new IntegrationId("myanimelist");

    @Inject
    ICalCalendarService iCalService;
    @Inject
    CalendarService calendarService;
    @Inject
    CalendarAnimeLinkService calendarLinkService;
    @Inject
    AnimeIntegrationLinkService integrationLinkService;

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

    public String getByMyAnimeListId(Set<IntegrationAnimeId> integrationIds, RequestContext context) {
        Set<AnimeId> ids = integrationIds.stream()
                                         .flatMap(id -> this.integrationLinkService.getAllWithId(
                                                 MY_ANIME_LIST_INTEGRATION,
                                                 id,
                                                 context))
                                         .map(AnimeIntegrationLink::animeId)
                                         .collect(Collectors.toSet());

        // TODO 2024-12-16: Make options an rest argument
        return this.iCalService.build(ids, AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE).build(), context).raw();
    }
}
