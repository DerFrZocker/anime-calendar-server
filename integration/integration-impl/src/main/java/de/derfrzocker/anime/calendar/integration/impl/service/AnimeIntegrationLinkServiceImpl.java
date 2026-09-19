package de.derfrzocker.anime.calendar.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.integration.dao.AnimeIntegrationLinkDao;
import de.derfrzocker.anime.calendar.integration.service.AnimeIntegrationLinkService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static de.derfrzocker.anime.calendar.integration.exception.AnimeIntegrationLinkExceptions.alreadyCreated;

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
    public Stream<AnimeIntegrationLink> getAllWithIds(IntegrationId integrationId,
                                                      Collection<IntegrationAnimeId> integrationAnimeIds,
                                                      RequestContext context) {
        return this.dao.getAllWithIds(integrationId, integrationAnimeIds, context);
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

        this.dao.create(link, context);
        this.eventPublisher.firePostCreate(link, createData, context);

        return link;
    }
}
