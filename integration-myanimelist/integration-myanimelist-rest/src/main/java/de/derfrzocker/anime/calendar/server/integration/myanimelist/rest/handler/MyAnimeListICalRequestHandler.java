package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.rest.api.AnimeOptionsQueryParams;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.ical.ICalCalendarBuilder;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationUserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class MyAnimeListICalRequestHandler {

    private static final UserId ICAL_USER = new UserId("UICALHANDL");

    @Inject
    ICalCalendarBuilder iCalCalendarBuilder;
    @Inject
    AnimeIntegrationLinkService integrationLinkService;
    @Inject
    IntegrationUserService userService;

    public String getByIds(Set<IntegrationAnimeId> integrationIds, AnimeOptionsQueryParams options) {
        RequestContext context = createRequestContext();
        return getByIds(integrationIds, options, context);
    }

    public String getByUser(IntegrationUserId userId, AnimeOptionsQueryParams options) {
        RequestContext context = createRequestContext();
        return getByUser(userId, options, context);
    }

    private String getByUser(IntegrationUserId userId, AnimeOptionsQueryParams options, RequestContext context) {
        return getByIds(this.userService.getAnimeIds(IntegrationIds.MY_ANIME_LIST, userId, context), options, context);
    }

    private String getByIds(Set<IntegrationAnimeId> integrationIds,
                            AnimeOptionsQueryParams options,
                            RequestContext context) {
        Set<AnimeId> ids = this.integrationLinkService.getAllWithIds(IntegrationIds.MY_ANIME_LIST,
                                                                     integrationIds,
                                                                     context)
                                                      .map(AnimeIntegrationLink::animeId)
                                                      .collect(Collectors.toSet());

        AnimeOptionsBuilder builder = options.toBuilder().withIntegrationId(IntegrationIds.MY_ANIME_LIST);

        return this.iCalCalendarBuilder.build(ids, builder.build(), context).raw();
    }

    private RequestContext createRequestContext() {
        return new RequestContext(ICAL_USER, Instant.now());
    }
}
