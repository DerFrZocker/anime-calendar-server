package de.derfrzocker.anime.calendar.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.integration.dao.AnimeIntegrationLinkDao;
import de.derfrzocker.anime.calendar.integration.mongodb.data.AnimeIntegrationLinkDO;
import de.derfrzocker.anime.calendar.integration.mongodb.mapper.AnimeIntegrationLinkDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static de.derfrzocker.anime.calendar.integration.mongodb.mapper.AnimeIntegrationLinkDataMapper.toData;

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
                                    integrationAnimeId.raw()).stream().map(AnimeIntegrationLinkDataMapper::toDomain);
    }

    @Override
    public Stream<AnimeIntegrationLink> getAllWithIds(IntegrationId integrationId,
                                                      Collection<IntegrationAnimeId> integrationAnimeIds,
                                                      RequestContext context) {
        return this.repository.find("integrationId = ?1 and integrationAnimeId in ?2",
                                    integrationId.raw(),
                                    integrationAnimeIds.stream().map(IntegrationAnimeId::raw).toList())
                              .stream()
                              .map(AnimeIntegrationLinkDataMapper::toDomain);
    }

    @Override
    public Stream<AnimeIntegrationLink> getAllWithId(AnimeId animeId,
                                                     IntegrationId integrationId,
                                                     RequestContext context) {
        return this.repository.find("animeId = ?1 and integrationId = ?2", animeId.raw(), integrationId.raw())
                              .stream()
                              .map(AnimeIntegrationLinkDataMapper::toDomain);
    }

    @Override
    public Optional<AnimeIntegrationLink> getById(AnimeId animeId,
                                                  IntegrationId integrationId,
                                                  IntegrationAnimeId integrationAnimeId,
                                                  RequestContext context) {
        return findById(animeId, integrationId, integrationAnimeId).map(AnimeIntegrationLinkDataMapper::toDomain);
    }

    @Override
    public void create(AnimeIntegrationLink link, RequestContext context) {
        this.repository.persist(toData(link));
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
