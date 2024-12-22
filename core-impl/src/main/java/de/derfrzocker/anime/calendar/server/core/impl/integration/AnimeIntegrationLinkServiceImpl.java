package de.derfrzocker.anime.calendar.server.core.impl.integration;

import de.derfrzocker.anime.calendar.server.core.api.integration.AnimeIntegrationLinkDao;
import de.derfrzocker.anime.calendar.server.core.api.integration.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
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
                                               RequestContext context) {
        Optional<AnimeIntegrationLink> optional = getById(animeId, integrationId, integrationAnimeId, context);
        if (optional.isPresent()) {
            // TODO 2024-12-07: Better exception
            throw new BadRequestException();
        }

        AnimeIntegrationLink link = AnimeIntegrationLink.from(animeId, integrationId, integrationAnimeId, context);

        this.eventPublisher.firePreCreateEvent(animeId, integrationId, integrationAnimeId, link, context);
        this.dao.create(link, context);
        this.eventPublisher.firePostCreateEvent(animeId, integrationId, integrationAnimeId, link, context);

        return link;
    }
}
