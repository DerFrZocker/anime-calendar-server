package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;

public record PreTrackingChannelNotificationActionDeleteEvent(TrackingChannelNotificationAction action,
                                                              RequestContext context) {

}
