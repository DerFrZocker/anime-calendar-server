package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionUpdateData;

public record PostTrackingChannelNotificationActionUpdateEvent(TrackingChannelNotificationAction current,
                                                               TrackingChannelNotificationAction updated,
                                                               TrackingChannelNotificationActionUpdateData updateData,
                                                               RequestContext context) {

}
