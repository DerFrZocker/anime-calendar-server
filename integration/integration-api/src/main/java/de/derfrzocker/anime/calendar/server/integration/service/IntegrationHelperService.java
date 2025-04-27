package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

public interface IntegrationHelperService {

    String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId);
}
