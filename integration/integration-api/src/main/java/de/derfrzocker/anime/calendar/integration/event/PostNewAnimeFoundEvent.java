package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.integration.name.api.NameSearchResult;
import java.util.List;

public record PostNewAnimeFoundEvent(IntegrationId fromIntegration, IntegrationAnimeId fromAnimeId,
                                     String fromAnimeTitle, List<NameSearchResult> potentialNames, int episodeCount,
                                     RequestContext context) {

}
