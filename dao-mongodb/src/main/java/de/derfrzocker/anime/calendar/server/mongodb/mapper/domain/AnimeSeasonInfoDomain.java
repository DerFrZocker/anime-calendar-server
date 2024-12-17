package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.mongodb.data.season.AnimeSeasonInfoDO;

public final class AnimeSeasonInfoDomain {

    public static AnimeSeasonInfoDO toData(AnimeSeasonInfo domain) {
        AnimeSeasonInfoDO data = new AnimeSeasonInfoDO();

        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.year = domain.year();
        data.season = domain.season();
        data.apply(domain);

        return data;
    }

    private AnimeSeasonInfoDomain() {

    }
}
