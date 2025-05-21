package de.derfrzocker.anime.calendar.server.season.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoCreateData;

public record PreAnimeSeasonInfoCreateEvent(AnimeSeasonInfo animeSeasonInfo, AnimeSeasonInfoCreateData createData,
                                            RequestContext context) {

}
