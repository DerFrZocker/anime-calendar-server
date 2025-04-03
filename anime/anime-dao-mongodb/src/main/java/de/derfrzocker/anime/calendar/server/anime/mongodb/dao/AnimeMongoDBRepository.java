package de.derfrzocker.anime.calendar.server.anime.mongodb.dao;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.AnimeDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeMongoDBRepository implements PanacheMongoRepositoryBase<AnimeDO, AnimeId> {

}
