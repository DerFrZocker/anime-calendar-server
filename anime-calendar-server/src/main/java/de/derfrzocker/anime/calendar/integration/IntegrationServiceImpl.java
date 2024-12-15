package de.derfrzocker.anime.calendar.integration;

import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarService;
import de.derfrzocker.anime.calendar.server.core.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.server.core.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.server.core.api.integration.IntegrationUserDao;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import de.derfrzocker.anime.calendar.server.model.domain.ical.Region;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class IntegrationServiceImpl implements IntegrationService {

    @Inject
    IntegrationAnimeDao integrationAnimeDao;
    @Inject
    Instance<IntegrationUserDao> integrationUserDao;
    @Inject
    ICalCalendarService calendarService;

    @Override
    public ICalCalendar getCalendar(IntegrationId integrationId, IntegrationUserId userId) {
        Set<IntegrationAnimeId> integrationAnimeIds = integrationUserDao.select(NamedLiteral.of(integrationId.raw() + "-user-dao"))
                                                                        .get()
                                                                        .getUserIds(userId);
        return getCalendar(integrationId, integrationAnimeIds);
    }

    @Override
    public ICalCalendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds) {
        Set<AnimeId> animeIds = integrationAnimeDao.getAnimeIds(integrationId, integrationAnimeIds);

        // TODO 2024-12-15: Null Request Context
        return calendarService.build(animeIds, new AnimeOptions(Region.DE_DE, true, null), null);
    }
}
