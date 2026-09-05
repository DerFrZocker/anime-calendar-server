package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelCreateData;

public record PreChannelCreateEvent(Channel channel, ChannelCreateData createData, RequestContext context) {

}
