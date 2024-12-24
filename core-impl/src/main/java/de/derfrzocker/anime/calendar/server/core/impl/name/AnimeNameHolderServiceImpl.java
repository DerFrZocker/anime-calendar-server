package de.derfrzocker.anime.calendar.server.core.impl.name;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderDao;
import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
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
            // TODO 2024-12-17: Better exception
            throw new BadRequestException();
        }

        AnimeNameHolder animeNameHolder = AnimeNameHolder.from(integrationId, integrationAnimeId, createData, context);

        this.eventPublisher.firePreCreateEvent(integrationId, integrationAnimeId, createData, animeNameHolder, context);
        this.dao.create(animeNameHolder, context);
        this.eventPublisher.firePostCreateEvent(integrationId,
                                                integrationAnimeId,
                                                createData,
                                                animeNameHolder,
                                                context);

        return animeNameHolder;
    }

    @Override
    public AnimeNameHolder updateWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          AnimeNameHolderUpdateData updateData,
                                          RequestContext context) {
        AnimeNameHolder current = getById(integrationId, integrationAnimeId, context).orElseThrow(
                ResourceNotFoundException.withAnimeName(integrationId, integrationAnimeId));
        AnimeNameHolder updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdateEvent(integrationId,
                                               integrationAnimeId,
                                               updateData,
                                               current,
                                               updated,
                                               context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdateEvent(integrationId,
                                                integrationAnimeId,
                                                updateData,
                                                current,
                                                updated,
                                                context);

        return updated;
    }

    @Override
    public void deleteById(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, RequestContext context) {
        AnimeNameHolder animeNameHolder = getById(integrationId, integrationAnimeId, context).orElseThrow(
                ResourceNotFoundException.withAnimeName(integrationId, integrationAnimeId));

        this.eventPublisher.firePreDeleteEvent(integrationId, integrationAnimeId, animeNameHolder, context);
        this.dao.delete(animeNameHolder, context);
        this.eventPublisher.firePostDeleteEvent(integrationId, integrationAnimeId, animeNameHolder, context);
    }
}
