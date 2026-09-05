package de.derfrzocker.anime.calendar.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.anime.api.AnimeCreateData;

public record PostAnimeCreateEvent(Anime anime, AnimeCreateData createData, RequestContext context) {

}
