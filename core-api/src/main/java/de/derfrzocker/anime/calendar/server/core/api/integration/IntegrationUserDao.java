package de.derfrzocker.anime.calendar.server.core.api.integration;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationUserId;
import java.util.Set;

public interface IntegrationUserDao {

    Set<IntegrationAnimeId> getUserIds(IntegrationUserId user);
}
