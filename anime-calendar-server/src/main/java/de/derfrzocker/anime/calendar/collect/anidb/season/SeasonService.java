package de.derfrzocker.anime.calendar.collect.anidb.season;

import de.derfrzocker.anime.calendar.collect.anidb.season.mongodb.SeasonDataDao;
import de.derfrzocker.anime.calendar.collect.anidb.season.udp.SeasonData;
import de.derfrzocker.anime.calendar.collect.anidb.season.udp.UDPSeasonDao;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SeasonService {

    @Inject
    UDPSeasonDao udpSeasonDao;
    @Inject
    SeasonDataDao seasonDataDao;
    @ConfigProperty(name = "anime.current-season")
    Season currentSeason;
    @ConfigProperty(name = "anime.current-year")
    int currentYear;

    public void updateSeason() {
        udpSeasonDao.getSeasonData().forEach(seasonDataDao::save);
    }

    public List<SeasonData> getSeasonData() {
        List<SeasonData> result = new ArrayList<>(seasonDataDao.getAll(currentSeason.previousSeason(), currentSeason.previousYear(currentYear)));
        result.addAll(seasonDataDao.getAll(currentSeason, currentYear));
        result.addAll(seasonDataDao.getAll(currentSeason.nextSeason(), currentSeason.nextYear(currentYear)));

        return result;
    }
}
