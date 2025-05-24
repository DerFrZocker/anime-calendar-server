package de.derfrzocker.anime.calendar.server.integration.name.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderUpdateData;

public record PreAnimeNameHolderUpdateEvent(AnimeNameHolder current, AnimeNameHolder updated,
                                            AnimeNameHolderUpdateData updateData, RequestContext context) {

}
