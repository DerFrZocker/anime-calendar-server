package de.derfrzocker.anime.calendar.anime.mongodb.adapter;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.anime.mongodb.model.AnimeDO;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class AnimePanache implements ReactivePanacheMongoRepositoryBase<AnimeDO, AnimeId> {

}
