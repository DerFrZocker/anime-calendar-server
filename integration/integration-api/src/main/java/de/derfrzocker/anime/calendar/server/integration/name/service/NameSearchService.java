package de.derfrzocker.anime.calendar.server.integration.name.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import io.smallrye.mutiny.Multi;

public interface NameSearchService {

    Multi<NameSearchResult> search(IntegrationId integrationId, String name, RequestContext context);
}
