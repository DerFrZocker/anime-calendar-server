package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.AnimeScheduleProviderService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
class CollectEpisodeCountSubTask {

    @Inject
    AnimeScheduleProviderService service;

    Uni<Integer> executeAsync(AnimeScheduleHolder data, RequestContext context) {
        return Uni.createFrom()
                  .item(data)
                  .map(holder -> collectSchedule(holder, context))
                  .map(this::searchBiggestNumber);
    }

    private List<ProvidedAnimeSchedule> collectSchedule(AnimeScheduleHolder data, RequestContext context) {
        try (var stream = this.service.provideAllWithData(data.tidData().tid(),
                                                          data.tidData().trackingChannelId(),
                                                          context)) {
            return stream.toList();
        }
    }

    private int searchBiggestNumber(List<ProvidedAnimeSchedule> schedules) {
        return schedules.stream().mapToInt(ProvidedAnimeSchedule::episode).max().orElse(0);
    }
}
