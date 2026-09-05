package de.derfrzocker.anime.calendar.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.integration.mongodb.data.AnimeIntegrationLinkDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeIntegrationLinkMongoDBRepository implements PanacheMongoRepository<AnimeIntegrationLinkDO> {

}
