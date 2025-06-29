package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class SyoboiNewAnimeAndTimeUpdateJob {

    @Inject
    CollectToAnimeScheduleHolderTask collectTask;
    @Inject
    CheckForNewSyoboiAnimeTask createTask;
    @Inject
    UpdateAnimeTimeFromSyoboiTask updateTask;

    public Uni<Void> executeAsync(List<ProvidedAnimeSchedule> schedules, RequestContext context) {
        var splitter = this.collectTask.collectAsync(sorted(schedules), context)
                                       .filter(this::isValid)
                                       .split(Operation.class, this::basedOnLinksPresent);
        var create = splitter.get(Operation.CREATE)
                             .collect()
                             .asList()
                             .onItem()
                             .transformToUni(list -> this.createTask.executeAsync(list, context));
        var update = splitter.get(Operation.UPDATE)
                             .onItem()
                             .transformToUniAndMerge(holder -> this.updateTask.executeAsync(holder, context))
                             .collect()
                             .asList()
                             .replaceWithVoid();

        return Uni.join().all(create, update).andCollectFailures().replaceWithVoid();
    }

    private List<ProvidedAnimeSchedule> sorted(List<ProvidedAnimeSchedule> schedules) {
        List<ProvidedAnimeSchedule> result = new ArrayList<>(schedules);

        result.sort(Comparator.comparing(ProvidedAnimeSchedule::startTime));

        return result;
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

    private Operation basedOnLinksPresent(AnimeScheduleHolder holder) {
        if (holder.links().isEmpty()) {
            return Operation.CREATE;
        }

        return Operation.UPDATE;
    }

    private enum Operation {
        CREATE, UPDATE
    }
}
