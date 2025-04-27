package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.SyoboiConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.AnimeScheduleProviderService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ResolvedTIDDataService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SyoboiAnimeCreateAndUpdateRequestHandler {

    private static final Logger LOG = Logger.getLogger(SyoboiAnimeCreateAndUpdateRequestHandler.class);

    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    SyoboiAnimeUpdateRequestHandler updateRequestHandler;
    @Inject
    SyoboiAnimeCreateRequestHandler createRequestHandler;
    @Inject
    ResolvedTIDDataService resolvedTIDDataService;
    @Inject
    AnimeScheduleProviderService scheduleProviderService;
    @Inject
    SyoboiConfig config;

    public Uni<Void> createOrUpdate(RequestContext context) {
        List<ProvidedAnimeSchedule> scheduleData;
        try (Stream<ProvidedAnimeSchedule> stream = getScheduleData(context)) {
            scheduleData = sort(stream);
        }

        LOG.info("Searching and updating anime schedule for '%s' schedule entries.".formatted(scheduleData.size()));

        return Multi.createFrom()
                    .iterable(scheduleData)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .map(data -> collectData(data, context))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(this::isValid)
                    .onItem()
                    .transformToUniAndMerge(data -> handleData(data, context))
                    .collect()
                    .asList()
                    .onFailure()
                    .invoke(d -> {
                        LOG.error("Syoboi anime creating or updating failed.", d);
                    })
                    .invoke(() -> {
                        LOG.info("Syoboi anime creating or updating successfully.");
                    })
                    .replaceWithVoid();
    }

    private Uni<Void> handleData(AnimeScheduleHolder data, RequestContext context) {
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(data.tidData().tid().raw());
        Stream<AnimeIntegrationLink> link = this.linkService.getAllWithId(IntegrationIds.SYOBOI,
                                                                          integrationAnimeId,
                                                                          context);
        try (link) {
            List<AnimeIntegrationLink> links = link.toList();
            if (links.isEmpty()) {
                return this.createRequestHandler.handleMissingLink(data, context);
            } else {
                return this.updateRequestHandler.handlePresentLink(links, data, context);
            }
        }
    }

    private Optional<AnimeScheduleHolder> collectData(ProvidedAnimeSchedule scheduleData, RequestContext context) {
        return this.resolvedTIDDataService.resolveById(scheduleData.tid(), context)
                                          .map(data -> new AnimeScheduleHolder(scheduleData, data));
    }

    private boolean isValid(AnimeScheduleHolder data) {
        if (data.tidData() == null) {
            return false;
        }

        if (!data.tidData().include()) {
            return false;
        }

        if (!Objects.equals(data.tidData().trackingChannelId(), data.schedule().channelId())) {
            return false;
        }

        if (data.tidData().end() == null) {
            return true;
        }

        if (data.tidData()
                .end()
                .isBefore(YearMonth.from(data.schedule().startTime().atZone(ZoneId.of(ZoneId.SHORT_IDS.get("JST")))))) {
            return false;
        }

        return true;
    }

    private Stream<ProvidedAnimeSchedule> getScheduleData(RequestContext context) {
        return this.scheduleProviderService.provideAllWithDate(LocalDate.now(),
                                                               this.config.getAnimeScheduleDays(),
                                                               context);
    }

    private List<ProvidedAnimeSchedule> sort(Stream<ProvidedAnimeSchedule> scheduleData) {
        List<ProvidedAnimeSchedule> sorted = new ArrayList<>(scheduleData.toList());

        sorted.sort(Comparator.comparing(ProvidedAnimeSchedule::startTime));

        return sorted;
    }
}
