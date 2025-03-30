package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import java.util.Optional;
import java.util.stream.Stream;

public interface ChannelDao {

    Stream<Channel> getAll(RequestContext context);

    Optional<Channel> getById(ChannelId id, RequestContext context);

    void create(Channel channel, RequestContext context);

    void update(Channel channel, RequestContext context);

    void delete(Channel channel, RequestContext context);
}
