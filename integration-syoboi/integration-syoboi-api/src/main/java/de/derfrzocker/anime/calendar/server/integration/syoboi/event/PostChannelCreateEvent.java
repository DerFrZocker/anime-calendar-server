package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PostChannelCreateEvent(Channel channel, ChannelCreateData createData, RequestContext context) {

}
