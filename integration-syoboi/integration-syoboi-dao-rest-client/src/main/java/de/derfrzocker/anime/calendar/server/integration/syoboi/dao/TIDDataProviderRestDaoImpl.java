package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByCountTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedTIDDataTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TitleMediumAndProgramByCountResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedTIDDataDataMapper;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.Dependent;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class TIDDataProviderRestDaoImpl implements TIDDataProviderDao {

    @RestClient
    TIDDataProviderRestClient restClient;

    @RateLimit(minSpacing = 1, minSpacingUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 16, delay = 2, delayUnit = ChronoUnit.SECONDS, retryOn = RateLimitException.class)
    @Override
    public Optional<ProvidedTIDData> provideById(TID id, RequestContext context) {
        return this.restClient.getTitleMediumAndFirstProgram(id.raw()).map(response -> toDomain(id, response));
    }

    private ProvidedTIDData toDomain(TID id, TitleMediumAndProgramByCountResponseTDO response) {
        if (response.Titles() == null || response.Titles().isEmpty()) {
            return null;
        }

        ProvidedTIDDataTDO data = response.Titles().get(id.raw());

        if (data == null) {
            return null;
        }

        TID tid = new TID(data.TID());
        String title = data.Title();
        YearMonth firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()), Integer.parseInt(data.FirstMonth()));
        YearMonth firstEnd = null;

        if (data.FirstEndYear() != null) {
            firstEnd = YearMonth.of(Integer.parseInt(data.FirstEndYear()), Integer.parseInt(data.FirstEndMonth()));
        }

        List<ChannelId> firstChannelIds = parseChannelIds(response.Programs());

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
