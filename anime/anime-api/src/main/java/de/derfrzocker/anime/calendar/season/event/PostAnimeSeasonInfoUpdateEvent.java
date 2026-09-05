package de.derfrzocker.anime.calendar.season.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.season.api.AnimeSeasonInfoUpdateData;

public record PostAnimeSeasonInfoUpdateEvent(AnimeSeasonInfo current, AnimeSeasonInfo updated,
                                             AnimeSeasonInfoUpdateData updateData, RequestContext context) {

}
