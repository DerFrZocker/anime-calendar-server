package de.derfrzocker.anime.calendar.integration;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
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
        Set<IntegrationAnimeId> integrationAnimeIds = integrationUserDao.select(NamedLiteral.of(integrationId.id() + "-user-dao")).get().getUserIds(userId);
        return getCalendar(integrationId, integrationAnimeIds);
    }

    @Override
    public Calendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds) {
        Set<AnimeId> animeIds = integrationAnimeDao.getAnimeIds(integrationId, integrationAnimeIds);

        return calendarService.buildCalendar(animeIds, new AnimeOptions(Region.DE_DE, true, null));
    }
}
