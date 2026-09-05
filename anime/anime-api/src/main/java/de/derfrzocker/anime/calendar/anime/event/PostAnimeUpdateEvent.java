package de.derfrzocker.anime.calendar.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.anime.api.AnimeUpdateData;

public record PostAnimeUpdateEvent(Anime current, Anime updated, AnimeUpdateData updateData, RequestContext context) {

}
