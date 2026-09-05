package de.derfrzocker.anime.calendar.anime.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

public record NewAnimeNotificationCreateData(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {

}
