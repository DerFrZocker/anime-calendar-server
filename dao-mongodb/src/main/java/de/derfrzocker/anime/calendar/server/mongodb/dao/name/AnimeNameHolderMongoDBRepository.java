package de.derfrzocker.anime.calendar.server.mongodb.dao.name;

import de.derfrzocker.anime.calendar.server.mongodb.data.name.AnimeNameHolderDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeNameHolderMongoDBRepository implements PanacheMongoRepository<AnimeNameHolderDO> {

}
