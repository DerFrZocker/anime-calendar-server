package de.derfrzocker.anime.calendar.server.integration.api;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

public record ManualLinkNotificationActionCreateData(AnimeId animeId, IntegrationId integrationId) {

}
