package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import java.time.LocalDate;
import java.util.stream.Stream;

public interface AnimeScheduleProviderDao {

    Stream<ProvidedAnimeSchedule> provideAllWithDate(LocalDate start, int days, RequestContext context);
}
