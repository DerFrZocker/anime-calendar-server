package de.derfrzocker.anime.calendar.integration.name.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.integration.name.api.AnimeNameHolderCreateData;

public record PostAnimeNameHolderCreateEvent(AnimeNameHolder animeNameHolder, AnimeNameHolderCreateData createData,
                                             RequestContext context) {

}
