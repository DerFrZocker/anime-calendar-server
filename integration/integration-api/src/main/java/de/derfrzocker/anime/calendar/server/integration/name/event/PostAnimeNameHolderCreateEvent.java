package de.derfrzocker.anime.calendar.server.integration.name.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolderCreateData;

public record PostAnimeNameHolderCreateEvent(AnimeNameHolder animeNameHolder, AnimeNameHolderCreateData createData,
                                             RequestContext context) {

}
