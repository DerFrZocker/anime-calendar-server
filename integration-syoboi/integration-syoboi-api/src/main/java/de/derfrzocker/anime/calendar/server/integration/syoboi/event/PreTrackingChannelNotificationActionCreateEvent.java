package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PreTrackingChannelNotificationActionCreateEvent(TrackingChannelNotificationAction action,
                                                              TrackingChannelNotificationActionCreateData createData,
                                                              RequestContext context) {

}
