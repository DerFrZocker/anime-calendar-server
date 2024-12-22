package de.derfrzocker.anime.calendar.server.mongodb.dao.integration;

import de.derfrzocker.anime.calendar.server.core.api.integration.AnimeIntegrationLinkDao;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.integration.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.mongodb.data.integration.AnimeIntegrationLinkDO;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.AnimeIntegrationLinkData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.AnimeIntegrationLinkDomain;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class AnimeIntegrationLinkMongoDBDaoImpl implements AnimeIntegrationLinkDao {

    @Inject
    AnimeIntegrationLinkMongoDBRepository repository;

    @Override
    public Stream<AnimeIntegrationLink> getAllWithId(IntegrationId integrationId,
                                                     IntegrationAnimeId integrationAnimeId,
                                                     RequestContext context) {
        return this.repository.find("integrationId = ?1 and integrationAnimeId = ?2",
                                    integrationId.raw(),
                                    integrationAnimeId.raw()).stream().map(AnimeIntegrationLinkData::toDomain);
    }

    @Override
    public Optional<AnimeIntegrationLink> getById(AnimeId animeId,
                                                  IntegrationId integrationId,
                                                  IntegrationAnimeId integrationAnimeId,
                                                  RequestContext context) {
        return findById(animeId, integrationId, integrationAnimeId).map(AnimeIntegrationLinkData::toDomain);
    }

    @Override
    public void create(AnimeIntegrationLink link, RequestContext context) {
        this.repository.persist(AnimeIntegrationLinkDomain.toData(link));
    }

    private Optional<AnimeIntegrationLinkDO> findById(AnimeId animeId,
                                                      IntegrationId integrationId,
                                                      IntegrationAnimeId integrationAnimeId) {
        return this.repository.find("animeId = ?1 and integrationId = ?2 and integrationAnimeId = ?3",
                                    animeId.raw(),
                                    integrationId.raw(),
                                    integrationAnimeId.raw()).firstResultOptional();
    }
}
