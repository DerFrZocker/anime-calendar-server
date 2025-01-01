package de.derfrzocker.anime.calendar.server.impl.syoboi.service;

import de.derfrzocker.anime.calendar.server.impl.syoboi.client.SyoboiRestClient;
import de.derfrzocker.anime.calendar.server.impl.syoboi.client.TitleMediumResponse;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.Channel;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TIDData;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.dao.SyoboiCompleteDataMongoDBDaoImpl;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.dao.SyoboiIncompleteDataMongoDBDaoImpl;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data.SyoboiCompleteDataDO;
import de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.data.SyoboiIncompleteDataDO;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// TODO 2024-12-26: Make this better
@Dependent
public class SyoboiTIDDataService {

    @RestClient
    SyoboiRestClient restClient;
    @Inject
    SyoboiCompleteDataMongoDBDaoImpl completeDataDao;
    @Inject
    SyoboiIncompleteDataMongoDBDaoImpl incompleteDataDao;
    // TODO 2024-12-26: Better config option
    @ConfigProperty(name = "syoboi-collection.incomplete-data-duration")
    Duration incompleteDataValidDuration;

    public Optional<TIDData> getById(TID id, RequestContext context) {
        return this.completeDataDao.getById(id, context)
                                   .map(this::toDomain)
                                   .or(() -> getIncomplete(id, context))
                                   .or(() -> readFresh(id, context));
    }

    private Optional<TIDData> getIncomplete(TID id, RequestContext context) {
        return this.incompleteDataDao.getById(id, context).filter(this::isValid).map(this::toDomain);
    }

    private boolean isValid(SyoboiIncompleteDataDO data) {
        return data.validUntil.isAfter(Instant.now());
    }

    @RateLimit(minSpacing = 1, minSpacingUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 16, delay = 2, delayUnit = ChronoUnit.SECONDS, retryOn = RateLimitException.class)
    Optional<TIDData> readFresh(TID id, RequestContext context) {
        TitleMediumResponse response = this.restClient.getTitleMedium("TitleMedium", id.raw());

        if (response == null || response.Titles() == null) {
            return Optional.empty();
        }

        TitleMediumResponse.Data data = response.Titles().get(id.raw());

        if (data == null) {
            return Optional.empty();
        }

        Optional<SyoboiIncompleteDataDO> presentIncompleteData = this.incompleteDataDao.getById(id, context);

        if (data.FirstEndYear() != null) {
            // Complete
            presentIncompleteData.ifPresent(incomplete -> this.incompleteDataDao.delete(incomplete, context));

            SyoboiCompleteDataDO completeData = new SyoboiCompleteDataDO();
            completeData.tid = id;
            completeData.title = data.Title();
            completeData.firstChannel = new Channel(data.FirstCh());
            completeData.firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()),
                                                   Integer.parseInt(data.FirstMonth()));
            completeData.firstEnd = YearMonth.of(Integer.parseInt(data.FirstEndYear()),
                                                 Integer.parseInt(data.FirstEndMonth()));
            completeData.include = presentIncompleteData.map(syoboiIncompleteDataDO -> syoboiIncompleteDataDO.include)
                                                        .orElse(true);

            this.completeDataDao.create(completeData, context);

            return Optional.of(toDomain(completeData));
        } else {
            // Incomplete
            SyoboiIncompleteDataDO incompleteData = new SyoboiIncompleteDataDO();
            incompleteData.tid = id;
            incompleteData.title = data.Title();
            incompleteData.firstChannel = new Channel(data.FirstCh());
            incompleteData.firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()),
                                                     Integer.parseInt(data.FirstMonth()));
            incompleteData.validUntil = Instant.now().plus(this.incompleteDataValidDuration);
            incompleteData.include = presentIncompleteData.map(d -> d.include).orElse(true);

            presentIncompleteData.ifPresentOrElse(d -> this.incompleteDataDao.update(incompleteData, context),
                                                  () -> this.incompleteDataDao.create(incompleteData, context));

            return Optional.of(toDomain(incompleteData));
        }
    }

    private TIDData toDomain(SyoboiCompleteDataDO data) {
        return new TIDData(data.tid, data.title, data.firstChannel, data.firstStart, data.firstEnd, data.include);
    }

    private TIDData toDomain(SyoboiIncompleteDataDO data) {
        return new TIDData(data.tid, data.title, data.firstChannel, data.firstStart, null, data.include);
    }
}
