package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface ChannelService {

    Stream<Channel> getAll(RequestContext context);

    Optional<Channel> getById(ChannelId id, RequestContext context);

    Channel createWithData(ChannelId id, ChannelCreateData createData, RequestContext context);

    Channel updateWithData(ChannelId id, ChannelUpdateData updateData, RequestContext context);

    void deleteById(ChannelId id, RequestContext context);
}
