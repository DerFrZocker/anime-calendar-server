package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import java.util.List;

public record PostSyoboiAnimeScheduleRetrieveEvent(List<ProvidedAnimeSchedule> schedules, RequestContext context) {

}
