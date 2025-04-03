package de.derfrzocker.anime.calendar.server.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;

public record PreAnimeUpdateEvent(Anime current, Anime updated, AnimeUpdateData updateData, RequestContext context) {

}
