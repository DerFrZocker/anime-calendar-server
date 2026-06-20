package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

public record NewAnimeNotificationCreateData(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {

}
