package de.derfrzocker.anime.calendar.server.mongodb.dao.anime;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.mongodb.data.anime.AnimeDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeMongoDBRepository implements PanacheMongoRepositoryBase<AnimeDO, AnimeId> {

}
