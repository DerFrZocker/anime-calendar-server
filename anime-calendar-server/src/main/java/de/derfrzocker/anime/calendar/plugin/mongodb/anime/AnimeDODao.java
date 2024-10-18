package de.derfrzocker.anime.calendar.plugin.mongodb.anime;

import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeDODao implements PanacheMongoRepositoryBase<AnimeDO, AnimeId> {

}
