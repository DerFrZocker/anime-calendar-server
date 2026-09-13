package de.derfrzocker.anime.calendar.external.schedule.in;

import de.derfrzocker.anime.calendar.RequestContext;
import io.smallrye.mutiny.Uni;

import java.time.LocalDate;

public interface ForUpdatingSchedule {

    Uni<Void> updateSchedule(LocalDate from, int days, RequestContext context);
}
