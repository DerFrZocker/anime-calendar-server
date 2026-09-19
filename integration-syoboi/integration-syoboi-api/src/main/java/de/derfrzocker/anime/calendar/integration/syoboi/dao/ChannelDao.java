package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;

import java.util.Optional;

public interface ChannelDao {

    Optional<Channel> getById(ChannelId id, RequestContext context);

    void create(Channel channel, RequestContext context);

    void update(Channel channel, RequestContext context);
}
