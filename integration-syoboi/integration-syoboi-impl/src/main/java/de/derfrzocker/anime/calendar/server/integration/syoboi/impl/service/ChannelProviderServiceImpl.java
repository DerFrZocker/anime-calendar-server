package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.ChannelProviderDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ChannelProviderService;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ChannelProviderServiceImpl implements ChannelProviderService {

    @Inject
    ChannelProviderDao dao;

    @Override
    public Optional<ProvidedChannel> provideById(ChannelId id, RequestContext context) {
        return this.dao.provideById(id, context);
    }
}
