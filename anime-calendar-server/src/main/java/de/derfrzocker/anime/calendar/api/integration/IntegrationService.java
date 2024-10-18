package de.derfrzocker.anime.calendar.api.integration;

import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationUserId;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;

public interface IntegrationService {

    Calendar getCalendar(IntegrationId integrationId, IntegrationUserId userId);

    Calendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> animeIds);
}
