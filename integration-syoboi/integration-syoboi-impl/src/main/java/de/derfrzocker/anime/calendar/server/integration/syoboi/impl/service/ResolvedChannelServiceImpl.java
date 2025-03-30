package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ChannelProviderService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ChannelService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ResolvedChannelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ResolvedChannelServiceImpl implements ResolvedChannelService {

    @Inject
    ChannelService service;
    @Inject
    ChannelProviderService providerService;

    @Override
    public Optional<ResolvedChannel> resolveById(ChannelId id, RequestContext context) {
        return this.service.getById(id, context).or(() -> createNew(id, context)).map(ResolvedChannel::from);
    }

    private Optional<Channel> createNew(ChannelId id, RequestContext context) {
        return this.providerService.provideById(id, context)
                                   .map(channel -> this.service.createWithData(id,
                                                                               new ChannelCreateData(channel.name()),
                                                                               context));
    }
}
