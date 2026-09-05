package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationActionCreateData;

public record PostTrackingChannelNotificationActionCreateEvent(TrackingChannelNotificationAction action,
                                                               TrackingChannelNotificationActionCreateData createData,
                                                               RequestContext context) {

}
