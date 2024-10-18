package de.derfrzocker.anime.calendar.api.integration.linker;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import java.util.Set;

public interface IntegrationLinkService {

    Set<IntegrationAnimeId> linkAnime(IntegrationId integrationId, Anime anime);
}
