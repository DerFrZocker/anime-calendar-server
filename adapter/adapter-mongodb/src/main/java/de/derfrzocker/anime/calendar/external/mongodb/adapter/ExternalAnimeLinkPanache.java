package de.derfrzocker.anime.calendar.external.mongodb.adapter;

import de.derfrzocker.anime.calendar.external.ExternalAnimeLinkId;
import de.derfrzocker.anime.calendar.external.mongodb.model.ExternalAnimeLinkDO;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExternalAnimeLinkPanache
        implements ReactivePanacheMongoRepositoryBase<ExternalAnimeLinkDO, ExternalAnimeLinkId> {

}
