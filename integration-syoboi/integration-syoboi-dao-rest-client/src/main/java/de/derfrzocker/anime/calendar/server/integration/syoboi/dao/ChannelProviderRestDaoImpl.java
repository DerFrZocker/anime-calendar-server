package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedChannelDataMapper;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ChannelProviderRestDaoImpl implements ChannelProviderDao {

    @RestClient
    ChannelProviderRestClient restClient;

    @RateLimit(minSpacing = 1, minSpacingUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 16, delay = 2, delayUnit = ChronoUnit.SECONDS, retryOn = RateLimitException.class)
    @Override
    public Optional<ProvidedChannel> provideById(ChannelId id, RequestContext context) {
        return Optional.ofNullable(this.restClient.getChannel(id.raw()))
                       .map(response -> response.chItems)
                       .map(items -> items.chItem)
                       .map(ProvidedChannelDataMapper::toDomain);
    }
}
