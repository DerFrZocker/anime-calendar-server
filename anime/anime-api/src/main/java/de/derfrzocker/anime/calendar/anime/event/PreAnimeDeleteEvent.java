package de.derfrzocker.anime.calendar.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.anime.api.Anime;

public record PreAnimeDeleteEvent(Anime anime, RequestContext context) {

}
