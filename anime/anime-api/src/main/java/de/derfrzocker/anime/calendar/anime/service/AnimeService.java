package de.derfrzocker.anime.calendar.anime.service;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.anime.api.AnimeCreateData;
import de.derfrzocker.anime.calendar.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
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
