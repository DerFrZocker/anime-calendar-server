package de.derfrzocker.anime.calendar.server.model.domain.event.anime;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import java.util.List;

public record PostNewAnimeFoundEvent(IntegrationId fromIntegration, IntegrationAnimeId fromAnimeId,
                                     String fromAnimeTitle, List<NameSearchResult> potentialNames,
                                     RequestContext context) {

}