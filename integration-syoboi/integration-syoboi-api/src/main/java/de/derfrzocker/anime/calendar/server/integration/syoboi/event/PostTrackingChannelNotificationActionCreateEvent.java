package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;

public record PostTrackingChannelNotificationActionCreateEvent(TrackingChannelNotificationAction action,
                                                               TrackingChannelNotificationActionCreateData createData,
                                                               RequestContext context) {

}
