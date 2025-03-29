package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.config.SyoboiConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByCountResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByCountTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedAnimeScheduleTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedTIDDataTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TitleMediumAndProgramResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedTIDDataDataMapper;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.SyoboiRateLimitService;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Dependent
public class TIDDataProviderRestDaoImpl implements TIDDataProviderDao {

    private static final Logger LOG = Logger.getLogger(TIDDataProviderRestDaoImpl.class);

    @RestClient
    TIDDataProviderRestClient restClient;
    @Inject
    SyoboiRateLimitService rateLimitService;
    @Inject
    SyoboiConfig syoboiConfig;

    @Override
    public Optional<ProvidedTIDData> provideById(TID id, RequestContext context) {
        return this.rateLimitService.rateLimit(() -> this.restClient.getTitleMediumAndProgramms(id.raw(),
                                                                                                this.syoboiConfig.getAnimeScheduleDays()))
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
            LOG.error("Could not parse first year for tid data '%s'.".formatted(tid.raw()), e);
            return null;
        }
        YearMonth firstEnd = null;

        if (data.FirstEndYear() != null) {
            firstEnd = YearMonth.of(Integer.parseInt(data.FirstEndYear()), Integer.parseInt(data.FirstEndMonth()));
        }

        List<ChannelId> firstChannelIds;

        if (response.Programs() != null) {
            firstChannelIds = response.Programs()
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
            LOG.error("Could not find any programs for tid data '%s'.".formatted(tid.raw()));
            firstChannelIds = List.of();
        }

        return ProvidedTIDDataDataMapper.toDomain(tid, title, firstStart, firstEnd, firstChannelIds);
    }

    private List<ChannelId> parseChannelIds(Map<String, ProgramByCountTDO> programs) {
        if (programs == null || programs.isEmpty()) {
            return List.of();
        }

        return programs.values()
                       .stream()
                       .filter(Objects::nonNull)
                       .map(ProgramByCountTDO::ChID)
                       .distinct()
                       .map(ChannelId::new)
                       .toList();
    }
}
