package de.derfrzocker.anime.calendar.server.season.mongodb.dao;

import de.derfrzocker.anime.calendar.server.season.mongodb.data.AnimeSeasonInfoDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeSeasonInfoMongoDBRepository implements PanacheMongoRepository<AnimeSeasonInfoDO> {

}
