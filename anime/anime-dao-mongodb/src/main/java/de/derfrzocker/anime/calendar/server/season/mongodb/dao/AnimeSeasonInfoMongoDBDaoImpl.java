package de.derfrzocker.anime.calendar.server.season.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.season.mongodb.mapper.AnimeSeasonInfoDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.dao.AnimeSeasonInfoDao;
import de.derfrzocker.anime.calendar.server.season.mongodb.data.AnimeSeasonInfoDO;
import de.derfrzocker.anime.calendar.server.season.mongodb.mapper.AnimeSeasonInfoDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class AnimeSeasonInfoMongoDBDaoImpl implements AnimeSeasonInfoDao {

    @Inject
    AnimeSeasonInfoMongoDBRepository repository;

    @Override
    public Stream<AnimeSeasonInfo> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(AnimeSeasonInfoDataMapper::toDomain);
    }

    @Override
    public Optional<AnimeSeasonInfo> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             int year,
                                             Season season,
                                             RequestContext context) {
        return findById(integrationId, integrationAnimeId, year, season).map(AnimeSeasonInfoDataMapper::toDomain);
    }

    @Override
    public void create(AnimeSeasonInfo animeSeasonInfo, RequestContext context) {
        this.repository.persist(toData(animeSeasonInfo));
    }

    @Override
    public void update(AnimeSeasonInfo animeSeasonInfo, RequestContext context) {
        findById(animeSeasonInfo.integrationId(),
                 animeSeasonInfo.integrationAnimeId(),
                 animeSeasonInfo.year(),
                 animeSeasonInfo.season()).ifPresent(data -> {
            AnimeSeasonInfoDO newData = toData(animeSeasonInfo);
            newData.id = data.id;
            this.repository.update(newData);
        });
    }

    @Override
    public void delete(AnimeSeasonInfo animeSeasonInfo, RequestContext context) {
        this.repository.delete("integrationId = ?1 and integrationAnimeId = ?2 and year = ?3 and season = ?4",
                               animeSeasonInfo.integrationId().raw(),
                               animeSeasonInfo.integrationAnimeId().raw(),
                               animeSeasonInfo.year(),
                               animeSeasonInfo.season());
    }

    private Optional<AnimeSeasonInfoDO> findById(IntegrationId integrationId,
                                                 IntegrationAnimeId integrationAnimeId,
                                                 int year,
                                                 Season season) {
        return this.repository.find("integrationId = ?1 and integrationAnimeId = ?2 and year = ?3 and season = ?4",
                                    integrationId.raw(),
                                    integrationAnimeId.raw(),
                                    year,
                                    season).firstResultOptional();
    }
}
