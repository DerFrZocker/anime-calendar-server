package de.derfrzocker.anime.calendar.server.ical.rest.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.rest.api.AnimeOptionsQueryParams;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.ical.ICalCalendarBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Set;

@ApplicationScoped
public class AnimeIdICalRequestHandler {

    private static final UserId ICAL_USER = UserId.of("UICALHANDL");

    @Inject
    ICalCalendarBuilder iCalCalendarBuilder;

    public String getByIds(Set<AnimeId> ids, IntegrationId integrationId, AnimeOptionsQueryParams options) {
        RequestContext context = createRequestContext();

        AnimeOptionsBuilder builder = options.toBuilder();

        if (integrationId != null) {
            builder.withIntegrationId(integrationId);
        }

        return this.iCalCalendarBuilder.build(ids, builder.build(), context).raw();
    }

    private RequestContext createRequestContext() {
        return new RequestContext(ICAL_USER, Instant.now());
    }
}
