package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PreChannelDeleteEvent(Channel channel, RequestContext context) {

}
