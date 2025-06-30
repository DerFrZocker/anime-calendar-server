package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import java.time.LocalDate;
import java.util.stream.Stream;

public interface AnimeScheduleProviderDao {

    Stream<ProvidedAnimeSchedule> provideAllWithData(LocalDate start, int days, RequestContext context);

    Stream<ProvidedAnimeSchedule> provideAllWithData(TID tid, ChannelId channelId, RequestContext context);
}
