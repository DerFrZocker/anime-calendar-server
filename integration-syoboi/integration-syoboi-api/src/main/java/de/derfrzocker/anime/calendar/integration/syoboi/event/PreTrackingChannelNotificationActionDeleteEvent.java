package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationAction;

public record PreTrackingChannelNotificationActionDeleteEvent(TrackingChannelNotificationAction action,
                                                              RequestContext context) {

}
