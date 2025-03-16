package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.service.SyoboiRateLimitService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedAnimeScheduleTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ScheduleResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedAnimeScheduleDataMapper;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class AnimeScheduleProviderRestDaoImpl implements AnimeScheduleProviderDao {

    @RestClient
    AnimeScheduleProviderRestClient restClient;
    @Inject
    SyoboiRateLimitService rateLimitService;

    @Override
    public Stream<ProvidedAnimeSchedule> provideAllWithDate(LocalDate start, int days, RequestContext context) {
        return this.rateLimitService.rateLimit(() -> this.restClient.getProgramByDate(start.toString(), days))
                                    .map(ScheduleResponseTDO::Programs)
                                    .map(Map::values)
                                    .stream()
                                    .flatMap(Collection::stream)
                                    .filter(data -> data.Count() != null)
                                    .map(this::toDomain);
    }

    private ProvidedAnimeSchedule toDomain(ProvidedAnimeScheduleTDO data) {
        TID tid = new TID(data.TID());
        ChannelId channelId = new ChannelId(data.ChID());
        int episode = Integer.parseInt(data.Count());
        Instant startTime = Instant.ofEpochSecond(Long.parseLong(data.StTime()));
        Instant endTime = Instant.ofEpochSecond(Long.parseLong(data.EdTime()));

        return ProvidedAnimeScheduleDataMapper.toDomain(tid, channelId, episode, startTime, endTime);
    }
}
