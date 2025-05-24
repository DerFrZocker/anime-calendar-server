package de.derfrzocker.anime.calendar.server.integration.impl.name.service;

import static de.derfrzocker.anime.calendar.server.integration.name.exception.AnimeNameHolderExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.integration.name.exception.AnimeNameHolderExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.name.exception.AnimeNameHolderExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderUpdateData;
import de.derfrzocker.anime.calendar.server.integration.name.dao.AnimeNameHolderDao;
import de.derfrzocker.anime.calendar.server.integration.name.service.AnimeNameHolderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class AnimeNameHolderServiceImpl implements AnimeNameHolderService {

    @Inject
    AnimeNameHolderDao dao;
    @Inject
    AnimeNameHolderEventPublisher eventPublisher;

    @Override
    public Stream<AnimeNameHolder> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Stream<AnimeNameHolder> getAllWithId(IntegrationId integrationId, RequestContext context) {
        return this.dao.getAllWithId(integrationId, context);
    }

    @Override
    public Optional<AnimeNameHolder> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             RequestContext context) {
        return this.dao.getById(integrationId, integrationAnimeId, context);
    }

    @Override
    public AnimeNameHolder createWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          AnimeNameHolderCreateData createData,
                                          RequestContext context) {
        Optional<AnimeNameHolder> optional = getById(integrationId, integrationAnimeId, context);
        if (optional.isPresent()) {
            throw alreadyCreated(integrationId, integrationAnimeId).get();
        }

        AnimeNameHolder action = AnimeNameHolder.from(integrationId, integrationAnimeId, createData, context);

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return getById(integrationId, integrationAnimeId, context).orElseThrow(inconsistentNotFound(integrationId,
                                                                                                    integrationAnimeId));
    }

    @Override
    public AnimeNameHolder updateWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          AnimeNameHolderUpdateData updateData,
                                          RequestContext context) {
        AnimeNameHolder current = getById(integrationId,
                                          integrationAnimeId,
                                          context).orElseThrow(notFound(integrationId, integrationAnimeId));
        AnimeNameHolder updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(integrationId, integrationAnimeId, context).orElseThrow(inconsistentNotFound(integrationId,
                                                                                                    integrationAnimeId));
    }

    @Override
    public void deleteById(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, RequestContext context) {
        AnimeNameHolder animeNameHolder = getById(integrationId, integrationAnimeId, context).orElseThrow(notFound(
                integrationId,
                integrationAnimeId));

        this.eventPublisher.firePreDelete(animeNameHolder, context);
        this.dao.delete(animeNameHolder, context);
        this.eventPublisher.firePostDelete(animeNameHolder, context);
    }
}
