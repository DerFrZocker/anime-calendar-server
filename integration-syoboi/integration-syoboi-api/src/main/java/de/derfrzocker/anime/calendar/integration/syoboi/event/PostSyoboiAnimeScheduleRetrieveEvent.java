package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ProvidedAnimeSchedule;
import java.util.List;

public record PostSyoboiAnimeScheduleRetrieveEvent(List<ProvidedAnimeSchedule> schedules, RequestContext context) {

}
