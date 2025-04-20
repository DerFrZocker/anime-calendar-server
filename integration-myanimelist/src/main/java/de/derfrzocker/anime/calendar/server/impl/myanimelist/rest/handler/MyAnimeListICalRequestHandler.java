package de.derfrzocker.anime.calendar.server.impl.myanimelist.rest.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.user.MyAnimeListIntegrationUserService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class MyAnimeListICalRequestHandler {

    private static final UserId ICAL_USER = new UserId("UICALHANDL");
    private static final IntegrationId MY_ANIME_LIST_INTEGRATION = new IntegrationId("myanimelist");

    @Inject
    ICalCalendarService iCalService;
    @Inject
    AnimeIntegrationLinkService integrationLinkService;
    @Inject
    MyAnimeListIntegrationUserService userService;

    public String getByIds(Set<IntegrationAnimeId> integrationIds) {
        RequestContext context = createRequestContext();
        Set<AnimeId> ids = integrationIds.stream()
                                         .flatMap(id -> this.integrationLinkService.getAllWithId(
                                                 MY_ANIME_LIST_INTEGRATION,
                                                 id,
                                                 context))
                                         .map(AnimeIntegrationLink::animeId)
                                         .collect(Collectors.toSet());

        // TODO 2024-12-16: Make options an rest argument
        return this.iCalService.build(ids,
                                      AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE)
                                                         .withIntegrationId(MY_ANIME_LIST_INTEGRATION)
                                                         .build(),
                                      context).raw();
    }

    public String getByUser(IntegrationUserId userId) {
        return getByIds(this.userService.getAnimeIds(userId));
    }

    private RequestContext createRequestContext() {
        return new RequestContext(ICAL_USER, Instant.now());
    }
}
