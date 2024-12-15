package de.derfrzocker.anime.calendar.server.core.api.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeUpdateData;
import java.util.Optional;

public interface AnimeService {

    Optional<Anime> getById(AnimeId id, RequestContext context);

    Anime createWithData(AnimeCreateData createData, RequestContext context);

    Anime updateWithData(AnimeId id, AnimeUpdateData updateData, RequestContext context);

    void deleteById(AnimeId id, RequestContext context);
}
