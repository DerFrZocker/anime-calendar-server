package de.derfrzocker.anime.calendar.server.episode.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import java.util.List;

public record AnimeOptions(Region region, boolean useRegionName, IntegrationId integrationId,
                           List<StreamType> streamTypes) {

}
