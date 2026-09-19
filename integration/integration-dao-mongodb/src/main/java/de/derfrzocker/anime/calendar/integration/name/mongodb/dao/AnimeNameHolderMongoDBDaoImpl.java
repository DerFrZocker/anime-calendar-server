package de.derfrzocker.anime.calendar.integration.name.mongodb.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.integration.name.dao.AnimeNameHolderDao;
import de.derfrzocker.anime.calendar.integration.name.mongodb.data.AnimeNameHolderDO;
import de.derfrzocker.anime.calendar.integration.name.mongodb.mapper.AnimeNameHolderDataMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.stream.Stream;

import static de.derfrzocker.anime.calendar.integration.name.mongodb.mapper.AnimeNameHolderDataMapper.toData;

@ApplicationScoped
public class AnimeNameHolderMongoDBDaoImpl implements AnimeNameHolderDao {

    @Inject
    AnimeNameHolderMongoDBRepository repository;

    @Override
    public Stream<AnimeNameHolder> getAllWithId(IntegrationId integrationId, RequestContext context) {
        return this.repository.find("integrationId", integrationId.raw())
                              .stream()
                              .map(AnimeNameHolderDataMapper::toDomain);
    }

    @Override
    public Optional<AnimeNameHolder> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             RequestContext context) {
        return findById(integrationId, integrationAnimeId).map(AnimeNameHolderDataMapper::toDomain);
    }

    @Override
    public void create(AnimeNameHolder animeNameHolder, RequestContext context) {
        this.repository.persist(toData(animeNameHolder));
    }

    @Override
    public void update(AnimeNameHolder animeNameHolder, RequestContext context) {
        findById(animeNameHolder.integrationId(), animeNameHolder.integrationAnimeId()).ifPresent(data -> {
            AnimeNameHolderDO newData = toData(animeNameHolder);
            newData.id = data.id;
            this.repository.update(newData);
        });
    }

    private Optional<AnimeNameHolderDO> findById(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        return this.repository.find("integrationId = ?1 and integrationAnimeId = ?2",
                                    integrationId.raw(),
                                    integrationAnimeId.raw()).firstResultOptional();
    }
}
