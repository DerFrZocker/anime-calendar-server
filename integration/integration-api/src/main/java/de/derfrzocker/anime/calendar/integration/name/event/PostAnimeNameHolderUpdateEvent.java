package de.derfrzocker.anime.calendar.integration.name.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolderUpdateData;

public record PostAnimeNameHolderUpdateEvent(AnimeNameHolder current, AnimeNameHolder updated,
                                             AnimeNameHolderUpdateData updateData, RequestContext context) {

}
