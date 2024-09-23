package de.derfrzocker.anime.calendar.api.integration.linker;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;

public interface IntegrationLinkService {

    void linkAnime(IntegrationId integrationId, Anime anime);
}
