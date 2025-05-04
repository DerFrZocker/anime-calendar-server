package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
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
    ICalCalendarService iCalService;
    @Inject
    AnimeIntegrationLinkService integrationLinkService;
    @Inject
    IntegrationUserService userService;

    public String getByIds(Set<IntegrationAnimeId> integrationIds) {
        RequestContext context = createRequestContext();
        return getByIds(integrationIds, context);
    }

    public String getByUser(IntegrationUserId userId) {
        RequestContext context = createRequestContext();
        return getByUser(userId, context);
    }

    private String getByUser(IntegrationUserId userId, RequestContext context) {
        return getByIds(this.userService.getAnimeIds(IntegrationIds.MY_ANIME_LIST, userId, context));
    }

    private String getByIds(Set<IntegrationAnimeId> integrationIds, RequestContext context) {
        Set<AnimeId> ids = this.integrationLinkService.getAllWithIds(IntegrationIds.MY_ANIME_LIST,
                                                                     integrationIds,
                                                                     context)
                                                      .map(AnimeIntegrationLink::animeId)
                                                      .collect(Collectors.toSet());

        // TODO 2024-12-16: Make options an rest argument
        return this.iCalService.build(ids,
                                      AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE)
                                                         .withIntegrationId(IntegrationIds.MY_ANIME_LIST)
                                                         .build(),
                                      context).raw();
    }

    private RequestContext createRequestContext() {
        return new RequestContext(ICAL_USER, Instant.now());
    }
}
