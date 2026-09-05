package de.derfrzocker.anime.calendar.season.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfoCreateData;

public record PreAnimeSeasonInfoCreateEvent(AnimeSeasonInfo animeSeasonInfo, AnimeSeasonInfoCreateData createData,
                                            RequestContext context) {

}
