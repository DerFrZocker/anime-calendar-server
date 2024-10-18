package de.derfrzocker.anime.calendar.api.integration;

import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import java.util.Set;

public interface IntegrationAnimeDao {

    Set<AnimeId> getAnimeIds(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds);

    Set<IntegrationAnimeId> getIntegrationIds(IntegrationId integrationId, AnimeId animeId);

    void saveOrMerge(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, AnimeId animeId);
}
