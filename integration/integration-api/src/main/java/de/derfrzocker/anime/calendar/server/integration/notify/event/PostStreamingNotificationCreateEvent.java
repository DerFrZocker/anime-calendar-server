package de.derfrzocker.anime.calendar.server.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationCreateData;

public record PostStreamingNotificationCreateEvent(StreamingNotification notification,
                                                   StreamingNotificationCreateData createData,
                                                   RequestContext context) {

}
