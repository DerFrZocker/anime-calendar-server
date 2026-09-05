package de.derfrzocker.anime.calendar.episode.api;

import de.derfrzocker.anime.calendar.anime.api.Region;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import java.util.List;

public record AnimeOptions(Region region, boolean useRegionName, List<String> languagePriorities,
                           IntegrationId integrationId, List<StreamType> streamTypes) {

}
