package de.derfrzocker.anime.calendar.server.integration.name.mongodb.dao;

import de.derfrzocker.anime.calendar.server.integration.name.mongodb.data.AnimeNameHolderDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeNameHolderMongoDBRepository implements PanacheMongoRepository<AnimeNameHolderDO> {

}
