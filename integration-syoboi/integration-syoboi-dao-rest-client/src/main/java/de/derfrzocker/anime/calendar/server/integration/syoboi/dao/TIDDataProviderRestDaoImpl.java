package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.config.TIDDataProviderConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByCountResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByCountTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedAnimeScheduleTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedTIDDataTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TitleMediumAndProgramResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedTIDDataDataMapper;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.SyoboiRateLimitService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class TIDDataProviderRestDaoImpl implements TIDDataProviderDao {

    @RestClient
    TIDDataProviderRestClient restClient;
    @Inject
    SyoboiRateLimitService rateLimitService;
    @Inject
    TIDDataProviderConfig tidDataProviderConfig;

    @Override
    public Optional<ProvidedTIDData> provideById(TID id, RequestContext context) {
        return this.rateLimitService
                .rateLimit(() -> this.restClient.getTitleMediumAndProgramms(
                        id.raw(),
                        this.tidDataProviderConfig.programDays()))
                .map(response -> toDomain(id, response));
    }

    private ProvidedTIDData toDomain(TID id, TitleMediumAndProgramResponseTDO response) {
        if (response.Titles() == null || response.Titles().isEmpty()) {
            return null;
        }

        ProvidedTIDDataTDO data = response.Titles().get(id.raw());

        if (data == null) {
            return null;
        }

        TID tid = new TID(data.TID());
        String title = data.Title();
        YearMonth firstStart;
        try {
            firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()), Integer.parseInt(data.FirstMonth()));
        } catch (NumberFormatException e) {
            Log.errorf(e, "Could not parse first year for tid data '%s'.", tid.raw());
            return null;
        }
        YearMonth firstEnd = null;

        if (data.FirstEndYear() != null) {
            firstEnd = YearMonth.of(Integer.parseInt(data.FirstEndYear()), Integer.parseInt(data.FirstEndMonth()));
        }

        List<ChannelId> firstChannelIds;

        if (response.Programs() != null) {
            firstChannelIds = response
                    .Programs()
                    .values()
                    .stream()
                    .filter(schedule -> schedule.Count() != null)
                    .max(Comparator.comparing(ProvidedAnimeScheduleTDO::Count))
                    .stream()
                    .findFirst()
                    .flatMap(schedule -> this.rateLimitService.rateLimit(() -> this.restClient.getProgrammByCount(
                            id.raw(),
                            schedule.Count())))
                    .map(ProgramByCountResponseTDO::Programs)
                    .map(this::parseChannelIds)
                    .orElse(List.of());
        } else {
            Log.errorf("Could not find any programs for tid data '%s'.", tid.raw());
            firstChannelIds = List.of();
        }

        return ProvidedTIDDataDataMapper.toDomain(tid, title, firstStart, firstEnd, firstChannelIds);
    }

    private List<ChannelId> parseChannelIds(Map<String, ProgramByCountTDO> programs) {
        if (programs == null || programs.isEmpty()) {
            return List.of();
        }

        return programs
                .values()
                .stream()
                .filter(Objects::nonNull)
                .map(ProgramByCountTDO::ChID)
                .distinct()
                .map(ChannelId::new)
                .toList();
    }
}
