package de.derfrzocker.anime.calendar.server.integration.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.mongodb.data.AnimeIntegrationLinkDO;

public final class AnimeIntegrationLinkDataMapper {

    private AnimeIntegrationLinkDataMapper() {
    }

    public static AnimeIntegrationLinkDO toData(AnimeIntegrationLink domain) {
        AnimeIntegrationLinkDO data = new AnimeIntegrationLinkDO();

        data.animeId = domain.animeId();
        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.apply(domain);

        return data;
    }

    public static AnimeIntegrationLink toDomain(AnimeIntegrationLinkDO data) {
        return new AnimeIntegrationLink(data.animeId,
                                        data.integrationId,
                                        data.integrationAnimeId,
                                        data.createdAt,
                                        data.createdBy,
                                        data.updatedAt,
                                        data.updatedBy);
    }
}
