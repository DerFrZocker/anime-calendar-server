package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.mongodb.data.name.AnimeNameDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.name.AnimeNameHolderDO;
import java.util.List;

public final class AnimeNameHolderDomain {

    public static AnimeNameHolderDO toData(AnimeNameHolder domain) {
        AnimeNameHolderDO data = new AnimeNameHolderDO();

        data.integrationId = domain.integrationId();
        data.integrationAnimeId = domain.integrationAnimeId();
        data.names = toData(domain.names());
        data.apply(domain);

        return data;
    }

    private static List<AnimeNameDO> toData(List<AnimeName> domains) {
        if (domains == null) {
            return null;
        }

        return domains.stream().map(AnimeNameHolderDomain::toData).toList();
    }

    private static AnimeNameDO toData(AnimeName domain) {
        return new AnimeNameDO(domain.type(), domain.language(), domain.name());
    }

    private AnimeNameHolderDomain() {

    }
}
