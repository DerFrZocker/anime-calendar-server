package de.derfrzocker.anime.calendar.server.model.domain.event.anime;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;

public record PreAnimeDeleteEvent(Anime anime, RequestContext context) {

}
