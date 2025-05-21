package de.derfrzocker.anime.calendar.server.season.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.mongodb.data.AnimeSeasonInfoDO;

public final class AnimeSeasonInfoDataMapper {

    private AnimeSeasonInfoDataMapper() {
    }

    public static AnimeSeasonInfoDO toData(AnimeSeasonInfo domain) {
        AnimeSeasonInfoDO data = new AnimeSeasonInfoDO();

        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.year = domain.year();
        data.season = domain.season();
        data.apply(domain);

        return data;
    }

    public static AnimeSeasonInfo toDomain(AnimeSeasonInfoDO data) {
        return new AnimeSeasonInfo(data.integrationId,
                                   data.integrationAnimeId,
                                   data.year,
                                   data.season,
                                   data.createdAt,
                                   data.createdBy,
                                   data.updatedAt,
                                   data.updatedBy);
    }
}
