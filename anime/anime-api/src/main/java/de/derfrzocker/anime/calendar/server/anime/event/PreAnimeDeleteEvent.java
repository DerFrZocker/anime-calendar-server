package de.derfrzocker.anime.calendar.server.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;

public record PreAnimeDeleteEvent(Anime anime, RequestContext context) {

}
