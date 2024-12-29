package de.derfrzocker.anime.calendar.server.core.api.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import io.smallrye.mutiny.Multi;

public interface NameSearchService {

    Multi<NameSearchResult> search(IntegrationId integrationId, String name, RequestContext context);
}
