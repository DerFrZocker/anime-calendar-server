package de.derfrzocker.anime.calendar.server.core.api.integration.linker;

import de.derfrzocker.anime.calendar.server.model.domain.integration.linker.IntegrationNameIdData;
import java.util.Set;

public interface IntegrationAnimeNameDao {

    Set<IntegrationNameIdData> getAllAnimes();
}
