package de.derfrzocker.anime.calendar.server.model.domain.event.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import java.util.List;
import java.util.Map;

public record PostNameLinkSearchEvent(Anime anime, Map<IntegrationId, List<NameSearchResult>> searchResults,
                                      RequestContext context) {

}
