package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import java.util.Set;

public interface IntegrationUserDao {

    String NAME_SUFFIX = "-integration-user-dao";

    Set<IntegrationAnimeId> getAnimeIds(IntegrationUserId user, RequestContext context);
}
