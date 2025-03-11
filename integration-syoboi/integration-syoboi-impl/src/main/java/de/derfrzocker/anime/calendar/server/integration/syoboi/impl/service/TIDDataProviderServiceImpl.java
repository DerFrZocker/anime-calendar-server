package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.TIDDataProviderDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataProviderService;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class TIDDataProviderServiceImpl implements TIDDataProviderService {

    @Inject
    TIDDataProviderDao dao;

    @RateLimit(minSpacing = 1, minSpacingUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 16, delay = 2, delayUnit = ChronoUnit.SECONDS, retryOn = RateLimitException.class)
    @Override
    public Optional<ProvidedTIDData> provideById(TID id, RequestContext context) {
        return this.dao.provideById(id, context);
    }
}
