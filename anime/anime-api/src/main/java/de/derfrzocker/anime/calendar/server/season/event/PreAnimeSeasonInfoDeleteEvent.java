package de.derfrzocker.anime.calendar.server.season.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;

public record PreAnimeSeasonInfoDeleteEvent(AnimeSeasonInfo animeSeasonInfo, RequestContext context) {

}
