package de.derfrzocker.anime.calendar.collect.syoboi.link;

import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LinkService {

    @Inject
    LinkDataDao linkDataDao;

    public AnimeId getAnimeId(TID tid) {
        LinkData linkData = linkDataDao.getLinkData(tid);
        if (linkData == null) {
            return null;
        }

        return linkData.animeId();
    }
}
