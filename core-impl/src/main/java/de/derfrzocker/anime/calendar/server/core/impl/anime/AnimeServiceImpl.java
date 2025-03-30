package de.derfrzocker.anime.calendar.server.core.impl.anime;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeDao;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.core.impl.util.StringGenerator;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeUpdateData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;

@Dependent
public class AnimeServiceImpl implements AnimeService {

    @Inject
    AnimeDao dao;
    @Inject
    AnimeEventPublisher eventPublisher;

    @Override
    public Optional<Anime> getById(AnimeId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Anime createWithData(AnimeCreateData createData, RequestContext context) {
        AnimeId id = createNewAnimeId(context);

        Anime anime = Anime.from(id, createData, context);

        this.eventPublisher.firePreCreateEvent(createData, anime, context);
        this.dao.create(anime, context);
        this.eventPublisher.firePostCreateEvent(createData, anime, context);

        return anime;
    }

    @Override
    public Anime updateWithData(AnimeId id, AnimeUpdateData updateData, RequestContext context) {
        Anime current = getById(id, context).orElseThrow(ResourceNotFoundException.with(id));
        Anime updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdateEvent(id, updateData, current, updated, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdateEvent(id, updateData, current, updated, context);

        return updated;
    }

    @Override
    public void deleteById(AnimeId id, RequestContext context) {
        Anime anime = getById(id, context).orElseThrow(ResourceNotFoundException.with(id));

        this.eventPublisher.firePreDeleteEvent(anime, context);
        this.dao.delete(anime, context);
        this.eventPublisher.firePostDeleteEvent(anime, context);
    }

    private AnimeId createNewAnimeId(RequestContext context) {
        AnimeId animeId;
        do {
            animeId = StringGenerator.generateAnimeId();
        } while (getById(animeId, context).isPresent());

        return animeId;
    }
}
