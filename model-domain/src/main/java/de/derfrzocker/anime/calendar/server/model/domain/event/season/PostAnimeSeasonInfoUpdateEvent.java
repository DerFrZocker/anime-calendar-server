package de.derfrzocker.anime.calendar.server.model.domain.event.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;

public record PostAnimeSeasonInfoUpdateEvent(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                             int year, Season season, AnimeSeasonInfoUpdateData updateData,
                                             AnimeSeasonInfo current, AnimeSeasonInfo updated, RequestContext context) {

}