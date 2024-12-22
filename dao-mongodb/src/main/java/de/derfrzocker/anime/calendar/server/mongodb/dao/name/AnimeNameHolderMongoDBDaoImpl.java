package de.derfrzocker.anime.calendar.server.mongodb.dao.name;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderDao;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.mongodb.data.name.AnimeNameHolderDO;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.AnimeNameHolderData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.AnimeNameHolderDomain;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class AnimeNameHolderMongoDBDaoImpl implements AnimeNameHolderDao {

    @Inject
    AnimeNameHolderMongoDBRepository repository;

    @Override
    public Stream<AnimeNameHolder> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(AnimeNameHolderData::toDomain);
    }

    @Override
    public Optional<AnimeNameHolder> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             RequestContext context) {
        return findById(integrationId, integrationAnimeId).map(AnimeNameHolderData::toDomain);
    }

    @Override
    public void create(AnimeNameHolder animeNameHolder, RequestContext context) {
        this.repository.persist(AnimeNameHolderDomain.toData(animeNameHolder));
    }

    @Override
    public void update(AnimeNameHolder animeNameHolder, RequestContext context) {
        findById(animeNameHolder.integrationId(), animeNameHolder.integrationAnimeId()).ifPresent(data -> {
            AnimeNameHolderDO newData = AnimeNameHolderDomain.toData(animeNameHolder);
            newData.id = data.id;
            this.repository.update(newData);
        });
    }

    @Override
    public void delete(AnimeNameHolder animeNameHolder, RequestContext context) {
        this.repository.delete("integrationId = ?1 and integrationAnimeId = ?2",
                               animeNameHolder.integrationId().raw(),
                               animeNameHolder.integrationAnimeId().raw());
    }

    private Optional<AnimeNameHolderDO> findById(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        return this.repository.find("integrationId = ?1 and integrationAnimeId = ?2",
                                    integrationId.raw(),
                                    integrationAnimeId.raw()).firstResultOptional();
    }
}
