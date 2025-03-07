package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.server.integration.api.linker.IntegrationNameIdData;
import java.util.Set;

public interface IntegrationAnimeNameDao {

    Set<IntegrationNameIdData> getAllAnimes();
}
