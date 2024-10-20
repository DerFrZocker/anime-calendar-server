package de.derfrzocker.anime.calendar.collect.anidb.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeNamesListRepository implements PanacheMongoRepository<AnimeNamesListDO> {
}
