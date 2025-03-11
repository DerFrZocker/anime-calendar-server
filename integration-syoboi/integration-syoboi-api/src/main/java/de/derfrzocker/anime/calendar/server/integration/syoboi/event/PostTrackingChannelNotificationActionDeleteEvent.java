package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PostTrackingChannelNotificationActionDeleteEvent(TrackingChannelNotificationAction action,
                                                               RequestContext context) {

}
