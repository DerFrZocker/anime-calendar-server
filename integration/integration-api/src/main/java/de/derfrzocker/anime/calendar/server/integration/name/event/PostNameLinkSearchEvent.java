package de.derfrzocker.anime.calendar.server.integration.name.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import java.util.Collection;
import java.util.Map;

public record PostNameLinkSearchEvent(Anime anime, Map<IntegrationId, Collection<NameSearchResult>> searchResults,
                                      RequestContext context) {

}
