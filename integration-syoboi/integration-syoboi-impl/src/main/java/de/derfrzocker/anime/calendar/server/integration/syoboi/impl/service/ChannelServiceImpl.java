package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.ChannelExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.ChannelExceptions.notFound;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.ChannelDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ChannelService;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ChannelServiceImpl implements ChannelService {

    @Inject
    ChannelDao dao;
    @Inject
    ChannelEventPublisher eventPublisher;

    @Override
    public Stream<Channel> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<Channel> getById(ChannelId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Channel createWithData(ChannelId id, ChannelCreateData createData, RequestContext context) {
        Channel channel = Channel.from(id, createData, context);

        this.eventPublisher.firePreCreate(channel, createData, context);
        this.dao.create(channel, context);
        this.eventPublisher.firePostCreate(channel, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public Channel updateWithData(ChannelId id, ChannelUpdateData updateData, RequestContext context) {
        Channel current = getById(id, context).orElseThrow(notFound(id));
        Channel updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(ChannelId id, RequestContext context) {
        Channel channel = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(channel, context);
        this.dao.delete(channel, context);
        this.eventPublisher.firePostDelete(channel, context);
    }
}
