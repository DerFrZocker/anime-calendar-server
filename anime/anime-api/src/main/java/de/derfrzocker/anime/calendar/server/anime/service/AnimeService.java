package de.derfrzocker.anime.calendar.server.anime.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface AnimeService {

    Stream<Anime> getAll(RequestContext context);

    Stream<Anime> getAllByIds(Collection<AnimeId> ids, RequestContext context);

    Optional<Anime> getById(AnimeId id, RequestContext context);

    Anime createWithData(AnimeCreateData createData, RequestContext context);

    Anime createWithData(AnimeCreateData createData, RequestContext context, Consumer<Anime> prePostEventConsumer);

    Anime updateWithData(AnimeId id, AnimeUpdateData updateData, RequestContext context);

    void deleteById(AnimeId id, RequestContext context);
}
