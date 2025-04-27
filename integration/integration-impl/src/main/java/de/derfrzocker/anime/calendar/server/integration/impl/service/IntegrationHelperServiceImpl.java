package de.derfrzocker.anime.calendar.server.integration.impl.service;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationHelperService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntegrationHelperServiceImpl implements IntegrationHelperService {

    @Override
    public String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (IntegrationIds.ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.CRUNCHYROLL.equals(integrationId)) {
            return "https://www.crunchyroll.com/series/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s".formatted(integrationAnimeId.raw());
        }

        throw new IllegalArgumentException("Unknown integration id: " + integrationId);
    }
}
