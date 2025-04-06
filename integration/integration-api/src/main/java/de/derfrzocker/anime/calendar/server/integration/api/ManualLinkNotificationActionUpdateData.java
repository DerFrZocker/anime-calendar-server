package de.derfrzocker.anime.calendar.server.integration.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.util.Change;

public record ManualLinkNotificationActionUpdateData(Change<IntegrationAnimeId> integrationAnimeId) {

}
