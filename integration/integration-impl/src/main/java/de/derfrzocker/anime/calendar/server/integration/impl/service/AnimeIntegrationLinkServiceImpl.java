package de.derfrzocker.anime.calendar.server.integration.impl.service;

import static de.derfrzocker.anime.calendar.server.integration.exception.AnimeIntegrationLinkExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.integration.exception.AnimeIntegrationLinkExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkUpdateData;
import de.derfrzocker.anime.calendar.server.integration.dao.AnimeIntegrationLinkDao;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class AnimeIntegrationLinkServiceImpl implements AnimeIntegrationLinkService {

    @Inject
    AnimeIntegrationLinkDao dao;
    @Inject
    AnimeIntegrationLinkEventPublisher eventPublisher;

    @Override
    public Stream<AnimeIntegrationLink> getAllWithId(IntegrationId integrationId,
                                                     IntegrationAnimeId integrationAnimeId,
                                                     RequestContext context) {
        return this.dao.getAllWithId(integrationId, integrationAnimeId, context);
    }

    @Override
    public Stream<AnimeIntegrationLink> getAllWithId(AnimeId animeId,
                                                     IntegrationId integrationId,
                                                     RequestContext context) {
        return this.dao.getAllWithId(animeId, integrationId, context);
    }

    @Override
    public Optional<AnimeIntegrationLink> getById(AnimeId animeId,
                                                  IntegrationId integrationId,
                                                  IntegrationAnimeId integrationAnimeId,
                                                  RequestContext context) {
        return this.dao.getById(animeId, integrationId, integrationAnimeId, context);
    }

    @Override
    public AnimeIntegrationLink createWithData(AnimeId animeId,
                                               IntegrationId integrationId,
                                               IntegrationAnimeId integrationAnimeId,
                                               AnimeIntegrationLinkCreateData createData,
                                               RequestContext context) {
        Optional<AnimeIntegrationLink> optional = getById(animeId, integrationId, integrationAnimeId, context);
        if (optional.isPresent()) {
            throw alreadyCreated(animeId, integrationId, integrationAnimeId).get();
        }

        AnimeIntegrationLink link = AnimeIntegrationLink.from(animeId, integrationId, integrationAnimeId, context);

        this.eventPublisher.firePreCreate(link, createData, context);
        this.dao.create(link, context);
        this.eventPublisher.firePostCreate(link, createData, context);

        return link;
    }

    @Override
    public AnimeIntegrationLink updateWithData(AnimeId animeId,
                                               IntegrationId integrationId,
                                               IntegrationAnimeId integrationAnimeId,
                                               AnimeIntegrationLinkUpdateData updateData,
                                               RequestContext context) {
        AnimeIntegrationLink current = getById(animeId,
                                               integrationId,
                                               integrationAnimeId,
                                               context).orElseThrow(notFound(animeId,
                                                                             integrationId,
                                                                             integrationAnimeId));
        AnimeIntegrationLink updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return updated;
    }

    @Override
    public void deleteById(AnimeId animeId,
                           IntegrationId integrationId,
                           IntegrationAnimeId integrationAnimeId,
                           RequestContext context) {
        AnimeIntegrationLink link = getById(animeId, integrationId, integrationAnimeId, context).orElseThrow(notFound(
                animeId,
                integrationId,
                integrationAnimeId));

        this.eventPublisher.firePreDelete(link, context);
        this.dao.delete(link, context);
        this.eventPublisher.firePostDelete(link, context);
    }
}
