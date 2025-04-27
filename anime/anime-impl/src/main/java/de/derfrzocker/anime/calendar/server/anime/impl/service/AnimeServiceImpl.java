package de.derfrzocker.anime.calendar.server.anime.impl.service;

import static de.derfrzocker.anime.calendar.server.anime.exception.AnimeExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.anime.exception.AnimeExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.dao.AnimeDao;
import de.derfrzocker.anime.calendar.server.anime.impl.generator.AnimeIdGenerator;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@ApplicationScoped
public class AnimeServiceImpl implements AnimeService {

    @Inject
    AnimeDao dao;
    @Inject
    AnimeIdGenerator idGenerator;
    @Inject
    AnimeEventPublisher eventPublisher;

    @Override
    public Stream<Anime> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Stream<Anime> getAllByIds(Collection<AnimeId> ids, RequestContext context) {
        return this.dao.getAllByIds(ids, context);
    }

    @Override
    public Optional<Anime> getById(AnimeId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Anime createWithData(AnimeCreateData createData, RequestContext context) {
        return createWithData(createData, context, anime -> {
        });
    }

    @Override
    public Anime createWithData(AnimeCreateData createData,
                                RequestContext context,
                                Consumer<Anime> prePostEventConsumer) {
        AnimeId id = this.idGenerator.generateId(potential -> getById(potential, context).isPresent());
        Anime anime = Anime.from(id, createData, context);

        this.eventPublisher.firePreCreate(anime, createData, context);
        this.dao.create(anime, context);
        prePostEventConsumer.accept(anime);
        this.eventPublisher.firePostCreate(anime, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public Anime updateWithData(AnimeId id, AnimeUpdateData updateData, RequestContext context) {
        Anime current = getById(id, context).orElseThrow(notFound(id));
        Anime updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(AnimeId id, RequestContext context) {
        Anime anime = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(anime, context);
        this.dao.delete(anime, context);
        this.eventPublisher.firePostDelete(anime, context);
    }
}
