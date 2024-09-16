package de.derfrzocker.anime.calendar.api.integration;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import java.util.Set;

public interface IntegrationAnimeDao {

    Set<AnimeId> getAnimeIds(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds);
}
