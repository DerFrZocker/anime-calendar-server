package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import java.util.Set;

public interface IntegrationLinkService {

    Set<IntegrationAnimeId> linkAnime(IntegrationId integrationId, Anime anime);
}
