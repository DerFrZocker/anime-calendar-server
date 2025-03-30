package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.AnimeScheduleProviderDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.AnimeScheduleProviderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.stream.Stream;

@ApplicationScoped
public class AnimeScheduleProviderServiceImpl implements AnimeScheduleProviderService {

    @Inject
    AnimeScheduleProviderDao dao;

    @Override
    public Stream<ProvidedAnimeSchedule> provideAllWithDate(LocalDate start, int days, RequestContext context) {
        return this.dao.provideAllWithDate(start, days, context);
    }
}
