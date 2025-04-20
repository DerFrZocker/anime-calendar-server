package de.derfrzocker.anime.calendar.server.episode.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Region;

public record AnimeOptions(Region region, boolean useRegionName, IntegrationId integrationId, String streamType) {

}
