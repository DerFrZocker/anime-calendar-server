package de.derfrzocker.anime.calendar.collect.syoboi;

import de.derfrzocker.anime.calendar.collect.syoboi.mongodb.CompleteData;
import de.derfrzocker.anime.calendar.collect.syoboi.mongodb.IncompleteData;
import de.derfrzocker.anime.calendar.collect.syoboi.mongodb.MongoDBCompleteDataRepository;
import de.derfrzocker.anime.calendar.collect.syoboi.mongodb.MongoDBIncompleteDataRepository;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AnimeDataDao {

    @RestClient
    SyoboiTIDInfoRestClient syoboiTIDInfoRestClient;
    @Inject
    MongoDBCompleteDataRepository completeDataRepository;
    @Inject
    MongoDBIncompleteDataRepository incompleteDataRepository;
    @ConfigProperty(name = "syoboi-collection.incomplete-data-duration")
    Duration incompleteDataValidDuration;

    public TIDData getData(TID tid) {
        CompleteData completeData = completeDataRepository.findById(tid);
        if (completeData != null) {
            return fromCompleteData(completeData);
        }

        IncompleteData incompleteData = incompleteDataRepository.findById(tid);
        if (incompleteData != null && incompleteData.validUntil.isAfter(Instant.now())) {
            return fromIncompleteData(incompleteData);
        }


        TitleMediumResponse.Data data = getFromRest(tid);

        if (data == null) {
            throw new IllegalArgumentException("No anime data found for " + tid);
        }

        if (data.FirstEndYear() != null) {
            // Complete
            if (incompleteData != null) {
                incompleteDataRepository.delete(incompleteData);
            }

            completeData = new CompleteData();
            completeData.tid = tid;
            completeData.title = data.Title();
            completeData.firstChannel = new Channel(data.FirstCh());
            completeData.firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()), Integer.parseInt(data.FirstMonth()));
            completeData.firstEnd = YearMonth.of(Integer.parseInt(data.FirstEndYear()), Integer.parseInt(data.FirstEndMonth()));

            completeDataRepository.persist(completeData);

            return fromCompleteData(completeData);
        } else {
            // Incomplete

            incompleteData = new IncompleteData();
            incompleteData.tid = tid;
            incompleteData.title = data.Title();
            incompleteData.firstChannel = new Channel(data.FirstCh());
            incompleteData.firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()), Integer.parseInt(data.FirstMonth()));
            incompleteData.validUntil = Instant.now().plus(incompleteDataValidDuration);

            incompleteDataRepository.persistOrUpdate(incompleteData);

            return fromIncompleteData(incompleteData);
        }
    }

    @RateLimit(minSpacing = 1, minSpacingUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 16, delay = 2, delayUnit = ChronoUnit.SECONDS, retryOn = RateLimitException.class)
    TitleMediumResponse.Data getFromRest(TID tid) {
        TitleMediumResponse response = syoboiTIDInfoRestClient.getTitleMedium("TitleMedium", tid.tid());
        System.out.println(Instant.now());
        return response.Titles().get(tid.tid());
    }

    private TIDData fromCompleteData(CompleteData completeData) {
        return new TIDData(completeData.tid, completeData.title, completeData.firstChannel, completeData.firstStart, completeData.firstEnd);
    }

    private TIDData fromIncompleteData(IncompleteData incompleteData) {
        return new TIDData(incompleteData.tid, incompleteData.title, incompleteData.firstChannel, incompleteData.firstStart, null);
    }
}
