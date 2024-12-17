package de.derfrzocker.anime.calendar.server.mongodb.dao.season;

import de.derfrzocker.anime.calendar.server.mongodb.data.season.AnimeSeasonInfoDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeSeasonInfoMongoDBRepository implements PanacheMongoRepository<AnimeSeasonInfoDO> {

}
