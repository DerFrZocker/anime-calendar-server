package de.derfrzocker.anime.calendar.integration.name.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolder;

public record PostAnimeNameHolderDeleteEvent(AnimeNameHolder animeNameHolder, RequestContext context) {

}
