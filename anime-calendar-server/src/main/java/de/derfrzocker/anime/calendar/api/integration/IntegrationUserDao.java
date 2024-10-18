package de.derfrzocker.anime.calendar.api.integration;

import java.util.Set;

public interface IntegrationUserDao {

    Set<IntegrationAnimeId> getUserIds(IntegrationUserId user);
}
