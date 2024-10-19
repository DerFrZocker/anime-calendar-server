package de.derfrzocker.anime.calendar.server.core.api.integration;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationUserId;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;

public interface IntegrationService {

    Calendar getCalendar(IntegrationId integrationId, IntegrationUserId userId);

    Calendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> animeIds);
}
