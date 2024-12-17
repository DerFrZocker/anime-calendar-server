package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.mongodb.data.name.AnimeNameDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.name.AnimeNameHolderDO;
import java.util.List;

public final class AnimeNameHolderData {

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

        return data.stream().map(AnimeNameHolderData::toDomain).toList();
    }

    private static AnimeName toDomain(AnimeNameDO data) {
        return new AnimeName(data.type(), data.language(), data.name());
    }

    private AnimeNameHolderData() {

    }
}
