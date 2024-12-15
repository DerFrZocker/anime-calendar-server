package de.derfrzocker.anime.calendar.server.rest.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.rest.transfer.anime.AnimeTO;

public final class AnimeDomain {

    public static AnimeTO toTransfer(Anime domain) {
        // TODO 2024-12-15: Give out layers
        return new AnimeTO(domain.id(), domain.title(), domain.episodeCount());
    }

    private AnimeDomain() {

    }
}
