package de.derfrzocker.anime.calendar.server.season.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoUpdateData;

public record PostAnimeSeasonInfoUpdateEvent(AnimeSeasonInfo current, AnimeSeasonInfo updated,
                                             AnimeSeasonInfoUpdateData updateData, RequestContext context) {

}
