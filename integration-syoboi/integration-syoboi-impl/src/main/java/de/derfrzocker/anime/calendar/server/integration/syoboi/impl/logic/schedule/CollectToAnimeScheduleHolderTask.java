package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ResolvedTIDDataService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
class CollectToAnimeScheduleHolderTask {

    @Inject
    ResolvedTIDDataService resolvedTIDDataService;
    @Inject
    AnimeIntegrationLinkService linkService;

    Multi<AnimeScheduleHolder> collectAsync(List<ProvidedAnimeSchedule> schedules, RequestContext context) {
        return Multi.createFrom()
                    .iterable(schedules)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .onItem()
                    .transformToUniAndMerge(schedule -> collectData(schedule, context))
                    .filter(Optional::isPresent)
                    .map(Optional::get);
    }

    private Uni<Optional<AnimeScheduleHolder>> collectData(ProvidedAnimeSchedule schedule, RequestContext context) {
        var dataUni = Uni.createFrom()
                         .item(() -> this.resolvedTIDDataService.resolveById(schedule.tid(), context))
                         .emitOn(Infrastructure.getDefaultExecutor());
        var linksUni = Uni.createFrom()
                          .item(() -> collectLinks(schedule, context))
                          .emitOn(Infrastructure.getDefaultExecutor());

        return Uni.combine()
                  .all()
                  .unis(dataUni, linksUni)
                  .with((oData, links) -> oData.map(data -> new AnimeScheduleHolder(schedule, data, links)));
    }

    private List<AnimeIntegrationLink> collectLinks(ProvidedAnimeSchedule scheduleData, RequestContext context) {
        IntegrationAnimeId id = new IntegrationAnimeId(scheduleData.tid().raw());
        try (var stream = this.linkService.getAllWithId(IntegrationIds.SYOBOI, id, context)) {
            return stream.toList();
        }
    }
}
