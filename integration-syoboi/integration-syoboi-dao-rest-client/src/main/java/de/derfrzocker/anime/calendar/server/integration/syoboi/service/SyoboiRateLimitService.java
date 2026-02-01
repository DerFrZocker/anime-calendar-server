package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class SyoboiRateLimitService {

    public void rateLimit(Runnable runnable) {
        rateLimit(() -> {
            runnable.run();
            return null;
        });
    }

    @RateLimit(minSpacing = 1, minSpacingUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 16, delay = 2, delayUnit = ChronoUnit.SECONDS, retryOn = RateLimitException.class)
    public <T> T rateLimit(Supplier<T> supplier) {
        return supplier.get();
    }
}
