package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelUpdateData;

public record PostChannelUpdateEvent(Channel current, Channel updated, ChannelUpdateData updateData,
                                     RequestContext context) {

}
