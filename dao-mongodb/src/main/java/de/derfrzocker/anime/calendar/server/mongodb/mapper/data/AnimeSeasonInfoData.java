package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.mongodb.data.season.AnimeSeasonInfoDO;

public final class AnimeSeasonInfoData {

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

    private AnimeSeasonInfoData() {

    }
}
