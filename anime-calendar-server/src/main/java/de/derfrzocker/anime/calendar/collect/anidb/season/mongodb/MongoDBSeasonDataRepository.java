package de.derfrzocker.anime.calendar.collect.anidb.season.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MongoDBSeasonDataRepository implements PanacheMongoRepository<SeasonDataDO> {
}
