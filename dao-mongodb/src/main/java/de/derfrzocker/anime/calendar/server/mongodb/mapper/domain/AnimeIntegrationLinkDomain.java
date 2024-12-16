package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.mongodb.data.integration.AnimeIntegrationLinkDO;

public final class AnimeIntegrationLinkDomain {

    public static AnimeIntegrationLinkDO toData(AnimeIntegrationLink domain) {
        AnimeIntegrationLinkDO data = new AnimeIntegrationLinkDO();

        data.animeId = domain.animeId();
        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.apply(domain);

        return data;
    }

    private AnimeIntegrationLinkDomain() {

    }
}
