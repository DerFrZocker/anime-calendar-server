package de.derfrzocker.anime.calendar.server.model.domain.event.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeUpdateData;

public record PreAnimeUpdateEvent(AnimeId id, AnimeUpdateData updateData, Anime current, Anime updated,
                                  RequestContext context) {

}
