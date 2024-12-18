package de.derfrzocker.anime.calendar.collect.anidb;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;

public record AnimeNameSearchResult(IntegrationAnimeId externalAnimeId, String name, int confidence) {
}
