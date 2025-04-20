package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

public record AnimeOptions(Region region, boolean useRegionName, IntegrationId integrationId, String streamType) {

}
