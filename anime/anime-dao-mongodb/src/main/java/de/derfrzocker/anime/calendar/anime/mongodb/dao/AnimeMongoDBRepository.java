package de.derfrzocker.anime.calendar.anime.mongodb.dao;

import de.derfrzocker.anime.calendar.anime.mongodb.data.AnimeDO;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeMongoDBRepository implements PanacheMongoRepositoryBase<AnimeDO, AnimeId> {

}
