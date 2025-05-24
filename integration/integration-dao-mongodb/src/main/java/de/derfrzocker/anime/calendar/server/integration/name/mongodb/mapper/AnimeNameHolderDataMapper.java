package de.derfrzocker.anime.calendar.server.integration.name.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeName;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.mongodb.data.AnimeNameDO;
import de.derfrzocker.anime.calendar.server.integration.name.mongodb.data.AnimeNameHolderDO;
import java.util.List;

public final class AnimeNameHolderDataMapper {

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

        return domains.stream().map(AnimeNameHolderDataMapper::toData).toList();
    }

    private static AnimeNameDO toData(AnimeName domain) {
        return new AnimeNameDO(domain.type(), domain.language(), domain.name());
    }

    public static AnimeNameHolder toDomain(AnimeNameHolderDO data) {
        return new AnimeNameHolder(data.integrationId,
                                   data.integrationAnimeId,
                                   data.createdAt,
                                   data.createdBy,
                                   data.updatedAt,
                                   data.updatedBy,
                                   toDomain(data.names));
    }

    private static List<AnimeName> toDomain(List<AnimeNameDO> data) {
        if (data == null) {
            return null;
        }

        return data.stream().map(AnimeNameHolderDataMapper::toDomain).toList();
    }

    private static AnimeName toDomain(AnimeNameDO data) {
        return new AnimeName(data.type(), data.language(), data.name());
    }


    private AnimeNameHolderDataMapper() {

    }
}
