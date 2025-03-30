package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import java.util.Set;

public interface IntegrationUserDao {

    Set<IntegrationAnimeId> getUserIds(IntegrationUserId user);
}
