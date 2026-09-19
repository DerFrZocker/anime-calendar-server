package de.derfrzocker.anime.calendar.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;

import java.util.Optional;

public interface ChannelService {

    Optional<Channel> getById(ChannelId id, RequestContext context);

    Channel createWithData(ChannelId id, ChannelCreateData createData, RequestContext context);
}
