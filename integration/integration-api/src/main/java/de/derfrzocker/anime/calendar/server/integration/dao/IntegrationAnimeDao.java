package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import java.util.Set;

public interface IntegrationAnimeDao {

    Set<AnimeId> getAnimeIds(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds);

    Set<IntegrationAnimeId> getIntegrationIds(IntegrationId integrationId, AnimeId animeId);

    void saveOrMerge(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, AnimeId animeId);
}
