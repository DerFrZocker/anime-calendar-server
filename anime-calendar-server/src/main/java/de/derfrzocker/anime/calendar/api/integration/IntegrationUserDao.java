package de.derfrzocker.anime.calendar.api.integration;

import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationUserId;
import java.util.Set;

public interface IntegrationUserDao {

    Set<IntegrationAnimeId> getUserIds(IntegrationUserId user);
}
