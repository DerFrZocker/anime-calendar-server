package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedTIDData;

public record AnimeScheduleHolder(ProvidedAnimeSchedule schedule, ResolvedTIDData tidData) {

}
