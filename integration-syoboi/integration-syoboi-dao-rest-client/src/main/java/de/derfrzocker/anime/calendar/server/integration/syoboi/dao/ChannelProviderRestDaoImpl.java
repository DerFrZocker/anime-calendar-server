package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.SyoboiRateLimitService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedChannelDataMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ChannelProviderRestDaoImpl implements ChannelProviderDao {

    @RestClient
    ChannelProviderRestClient restClient;
    @Inject
    SyoboiRateLimitService rateLimitService;

    @Override
    public Optional<ProvidedChannel> provideById(ChannelId id, RequestContext context) {
        return this.rateLimitService.rateLimit(() -> Optional.ofNullable(this.restClient.getChannel(id.raw())))
                                    .map(response -> response.chItems)
                                    .map(items -> items.chItem)
                                    .map(ProvidedChannelDataMapper::toDomain);
    }
}
