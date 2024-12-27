package de.derfrzocker.anime.calendar.server.impl.syoboi.handler;

import de.derfrzocker.anime.calendar.server.core.api.integration.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.impl.syoboi.client.ScheduleResponse;
import de.derfrzocker.anime.calendar.server.impl.syoboi.client.SyoboiRestClient;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.Channel;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.SyoboiAnimeData;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.SyoboiAnimeSchedule;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import de.derfrzocker.anime.calendar.server.impl.syoboi.service.SyoboiTIDDataService;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SyoboiAnimeCreateAndUpdateRequestHandler {

    private static final IntegrationId SYOBOI = new IntegrationId("syoboi");

    private static final Logger LOG = Logger.getLogger(SyoboiAnimeCreateAndUpdateRequestHandler.class);

    @RestClient
    SyoboiRestClient restClient;
    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    SyoboiAnimeUpdateRequestHandler updateRequestHandler;
    @Inject
    SyoboiAnimeCreateRequestHandler createRequestHandler;
    @Inject
    SyoboiTIDDataService tidDataService;
    @ConfigProperty(name = "syoboi.anime-create-and-update.days")
    int days;

    public Uni<Void> createOrUpdate(RequestContext context) {
        List<SyoboiAnimeSchedule> scheduleData = getSchedules(LocalDate.now(), this.days);
        scheduleData = sort(scheduleData);

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

    private Uni<Void> handleData(SyoboiAnimeData data, RequestContext context) {
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(data.tidData().tid().raw());
        Stream<AnimeIntegrationLink> link = this.linkService.getAllWithId(SYOBOI, integrationAnimeId, context);
        try (link) {
            List<AnimeIntegrationLink> links = link.toList();
            if (links.isEmpty()) {
                return this.createRequestHandler.handleMissingLink(data, context);
            } else {
                return this.updateRequestHandler.handlePresentLink(links, data, context);
            }
        }
    }

    private Optional<SyoboiAnimeData> collectData(SyoboiAnimeSchedule scheduleData, RequestContext context) {
        return this.tidDataService.getById(scheduleData.tid(), context)
                                  .map(data -> new SyoboiAnimeData(scheduleData, data));
    }

    private boolean isValid(SyoboiAnimeData data) {
        if (data.tidData() == null) {
            return false;
        }

        if (!data.tidData().include()) {
            return false;
        }

        if (!data.tidData().channel().equals(data.schedule().channel())) {
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

    private List<SyoboiAnimeSchedule> sort(List<SyoboiAnimeSchedule> scheduleData) {
        List<SyoboiAnimeSchedule> sorted = new ArrayList<>(scheduleData);

        sorted.sort(Comparator.comparing(SyoboiAnimeSchedule::startTime));

        return sorted;
    }

    private List<SyoboiAnimeSchedule> getSchedules(LocalDate startTime, int days) {
        ScheduleResponse response = this.restClient.getSchedule("ProgramByDate", startTime.toString(), days);

        return response.Programs()
                       .values()
                       .stream()
                       .filter(data -> data.Count() != null)
                       .map(data -> new SyoboiAnimeSchedule(new TID(data.TID()),
                                                            new Channel(data.ChName()),
                                                            Integer.parseInt(data.Count()),
                                                            Instant.ofEpochSecond(Long.parseLong(data.StTime())),
                                                            Instant.ofEpochSecond(Long.parseLong(data.EdTime()))))
                       .toList();
    }
}
