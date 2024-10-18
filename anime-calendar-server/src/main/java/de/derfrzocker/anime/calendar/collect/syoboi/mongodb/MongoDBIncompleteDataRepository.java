package de.derfrzocker.anime.calendar.collect.syoboi.mongodb;

import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MongoDBIncompleteDataRepository implements PanacheMongoRepositoryBase<IncompleteData, TID> {
}
