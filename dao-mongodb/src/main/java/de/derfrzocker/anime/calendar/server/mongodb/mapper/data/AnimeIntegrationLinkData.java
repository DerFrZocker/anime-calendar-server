package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.mongodb.data.integration.AnimeIntegrationLinkDO;

public final class AnimeIntegrationLinkData {

    public static AnimeIntegrationLink toDomain(AnimeIntegrationLinkDO data) {
        return new AnimeIntegrationLink(data.animeId,
                                        data.integrationId,
                                        data.integrationAnimeId,
                                        data.createdAt,
                                        data.createdBy,
                                        data.updatedAt,
                                        data.updatedBy);
    }

    private AnimeIntegrationLinkData() {

    }
}
