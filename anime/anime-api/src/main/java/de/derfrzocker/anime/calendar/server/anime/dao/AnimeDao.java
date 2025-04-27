package de.derfrzocker.anime.calendar.server.anime.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnimeDao {

    Stream<Anime> getAll(RequestContext context);

    Stream<Anime> getAllByIds(Collection<AnimeId> ids, RequestContext context);

    Optional<Anime> getById(AnimeId id, RequestContext context);

    void create(Anime anime, RequestContext context);

    void update(Anime anime, RequestContext context);

    void delete(Anime anime, RequestContext context);
}
