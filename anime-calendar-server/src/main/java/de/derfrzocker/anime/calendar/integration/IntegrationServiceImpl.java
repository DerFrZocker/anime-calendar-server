package de.derfrzocker.anime.calendar.integration;

import de.derfrzocker.anime.calendar.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserDao;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.Region;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;

@ApplicationScoped
public class IntegrationServiceImpl implements IntegrationService {

    @Inject
    IntegrationAnimeDao integrationAnimeDao;
    @Inject
    Instance<IntegrationUserDao> integrationUserDao;
    @Inject
    CalendarService calendarService;

    @Override
    public Calendar getCalendar(IntegrationId integrationId, IntegrationUserId userId) {
        Set<IntegrationAnimeId> integrationAnimeIds = integrationUserDao.select(NamedLiteral.of(integrationId.raw() + "-user-dao")).get().getUserIds(userId);
        return getCalendar(integrationId, integrationAnimeIds);
    }

    @Override
    public Calendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds) {
        Set<AnimeId> animeIds = integrationAnimeDao.getAnimeIds(integrationId, integrationAnimeIds);

        return calendarService.buildCalendar(animeIds, new AnimeOptions(Region.DE_DE, true, null));
    }
}
