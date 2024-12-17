package de.derfrzocker.anime.calendar.server.mongodb.dao.season;

import de.derfrzocker.anime.calendar.server.core.api.season.AnimeSeasonInfoDao;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import de.derfrzocker.anime.calendar.server.mongodb.data.season.AnimeSeasonInfoDO;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.AnimeSeasonInfoData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.AnimeSeasonInfoDomain;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@RequestScoped
public class AnimeSeasonInfoMongoDBDaoImpl implements AnimeSeasonInfoDao {

    @Inject
    AnimeSeasonInfoMongoDBRepository repository;

    @Override
    public Stream<AnimeSeasonInfo> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(AnimeSeasonInfoData::toDomain);
    }

    @Override
    public Optional<AnimeSeasonInfo> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             int year,
                                             Season season,
                                             RequestContext context) {
        return findById(integrationId, integrationAnimeId, year, season).map(AnimeSeasonInfoData::toDomain);
    }

    @Override
    public void create(AnimeSeasonInfo animeSeasonInfo, RequestContext context) {
        this.repository.persist(AnimeSeasonInfoDomain.toData(animeSeasonInfo));
    }

    @Override
    public void update(AnimeSeasonInfo animeSeasonInfo, RequestContext context) {
        findById(animeSeasonInfo.integrationId(),
                 animeSeasonInfo.integrationAnimeId(),
                 animeSeasonInfo.year(),
                 animeSeasonInfo.season()).ifPresent(data -> {
            AnimeSeasonInfoDO newData = AnimeSeasonInfoDomain.toData(animeSeasonInfo);
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
