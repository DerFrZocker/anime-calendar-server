package de.derfrzocker.anime.calendar.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.integration.syoboi.dao.ChannelDao;
import de.derfrzocker.anime.calendar.integration.syoboi.service.ChannelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.exception.ChannelExceptions.inconsistentNotFound;

@ApplicationScoped
public class ChannelServiceImpl implements ChannelService {

    @Inject
    ChannelDao dao;

    @Override
    public Optional<Channel> getById(ChannelId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Channel createWithData(ChannelId id, ChannelCreateData createData, RequestContext context) {
        Channel channel = Channel.from(id, createData, context);

        this.dao.create(channel, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
