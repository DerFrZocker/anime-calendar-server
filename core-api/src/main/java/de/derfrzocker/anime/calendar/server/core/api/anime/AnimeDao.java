package de.derfrzocker.anime.calendar.server.core.api.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import java.util.Optional;

public interface AnimeDao {

    Optional<Anime> getById(AnimeId id, RequestContext context);

    void create(Anime anime, RequestContext context);

    void update(Anime anime, RequestContext context);

    void delete(Anime anime, RequestContext context);
}
