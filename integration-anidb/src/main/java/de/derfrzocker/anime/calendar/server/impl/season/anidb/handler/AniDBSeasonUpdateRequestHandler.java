package de.derfrzocker.anime.calendar.server.impl.season.anidb.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.impl.season.anidb.client.AniDBSeasonInfo;
import de.derfrzocker.anime.calendar.server.impl.season.anidb.client.AniDBUDPClient;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.season.service.AnimeSeasonInfoService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AniDBSeasonUpdateRequestHandler {

    private static final Logger LOG = Logger.getLogger(AniDBSeasonUpdateRequestHandler.class);

    @Inject
    AniDBUDPClient udpClient;
    @Inject
    AnimeSeasonInfoService service;

    public Uni<Void> createOrUpdate(RequestContext context) {
        List<AniDBSeasonInfo> seasonInfos = udpClient.getSeasonData();

        LOG.info("Creating or updating '%d' anime season.".formatted(seasonInfos.size()));

        return Multi.createFrom()
                    .iterable(seasonInfos)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .invoke(info -> createOrUpdate(info, context))
                    .collect()
                    .asList()
                    .onFailure()
                    .invoke(d -> {
                        LOG.error("AniDB season creating or updating failed.", d);
                    })
                    .invoke(() -> {
                        LOG.info("AniDB season created or updated successfully.");
                    })
                    .replaceWithVoid();
    }

    private void createOrUpdate(AniDBSeasonInfo read, RequestContext context) {
        Optional<AnimeSeasonInfo> current = this.service.getById(IntegrationIds.ANIDB,
                                                                 read.integrationAnimeId(),
                                                                 getYear(read.startDate()),
                                                                 getSeason(read.startDate()),
                                                                 context);

        if (current.isPresent()) {
            update(read, current.get(), context);
        } else {
            create(read, context);
        }
    }

    private void create(AniDBSeasonInfo read, RequestContext context) {
        this.service.createWithData(IntegrationIds.ANIDB,
                                    read.integrationAnimeId(),
                                    getYear(read.startDate()),
                                    getSeason(read.startDate()),
                                    new AnimeSeasonInfoCreateData(),
                                    context);
    }

    private void update(AniDBSeasonInfo read, AnimeSeasonInfo animeSeasonInfo, RequestContext context) {
        // Since it currently only has keys, we don't need to do anything for updating
    }

    private int getYear(Instant from) {
        return from.atOffset(ZoneOffset.UTC).getYear();
    }

    private Season getSeason(Instant from) {
        return Season.getSeason(from.atOffset(ZoneOffset.UTC).getMonth());
    }
}
