package de.derfrzocker.anime.calendar.collect.syoboi.link;

import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import de.derfrzocker.anime.calendar.collect.syoboi.link.mongodb.LinkDataDO;
import de.derfrzocker.anime.calendar.collect.syoboi.link.mongodb.MongoDBLinkDataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LinkDataDao {

    @Inject
    MongoDBLinkDataRepository mongoDBLinkDataRepository;

    public LinkData getLinkData(TID tid) {
        LinkDataDO result = mongoDBLinkDataRepository.findById(tid);
        if (result == null) {
            return null;
        }

        return new LinkData(result.animeId, result.tid);
    }
}
