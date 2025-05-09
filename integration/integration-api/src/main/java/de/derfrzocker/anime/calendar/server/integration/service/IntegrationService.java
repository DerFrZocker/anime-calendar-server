package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.ical.api.ICalCalendar;
import java.util.Set;

public interface IntegrationService {

    ICalCalendar getCalendar(IntegrationId integrationId, IntegrationUserId userId);

    ICalCalendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> animeIds);
}
