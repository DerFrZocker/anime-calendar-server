package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import java.util.Set;

public interface IntegrationUserService {

    Set<IntegrationAnimeId> getAnimeIds(IntegrationId integrationId, IntegrationUserId user, RequestContext context);
}
