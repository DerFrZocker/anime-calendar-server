package de.derfrzocker.anime.calendar.collect.anidb.season.mongodb;

import de.derfrzocker.anime.calendar.collect.anidb.season.udp.SeasonData;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.ZoneOffset;
import java.util.List;

@ApplicationScoped
public class SeasonDataDao {

    @Inject
    MongoDBSeasonDataRepository repository;

    public List<SeasonData> getAll(Season season, int year) {
        return repository.find("season = ?1 and year = ?2", season, year)
                .stream()
                .map(data -> new SeasonData(data.externalAnimeId, data.startDate))
                .toList();
    }

    public void save(SeasonData seasonData) {
        boolean isPresent = repository
                .find("externalAnimeId", seasonData.externalAnimeId().raw())
                .stream()
                .filter(data -> isSameSeason(data, seasonData))
                .anyMatch(data -> isSameYear(data, seasonData));

        if (isPresent) {
            return;
        }

        SeasonDataDO seasonDataDO = new SeasonDataDO();
        seasonDataDO.externalAnimeId = seasonData.externalAnimeId();
        seasonDataDO.startDate = seasonData.startDate();
        seasonDataDO.season = Season.getSeason(seasonData.startDate().atOffset(ZoneOffset.UTC).getMonth());
        seasonDataDO.year = seasonData.startDate().atOffset(ZoneOffset.UTC).getYear();

        repository.persist(seasonDataDO);
    }

    private boolean isSameSeason(SeasonDataDO seasonDataDO, SeasonData seasonData) {
        return Season.getSeason(seasonData.startDate().atOffset(ZoneOffset.UTC).getMonth()).equals(seasonDataDO.season);
    }

    private boolean isSameYear(SeasonDataDO data, SeasonData seasonData) {
        return seasonData.startDate().atOffset(ZoneOffset.UTC).getYear() == data.year;
    }
}
