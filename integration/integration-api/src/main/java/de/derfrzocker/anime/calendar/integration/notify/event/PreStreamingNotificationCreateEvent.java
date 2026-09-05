package de.derfrzocker.anime.calendar.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationCreateData;

public record PreStreamingNotificationCreateEvent(StreamingNotification notification,
                                                  StreamingNotificationCreateData createData,
                                                  RequestContext context) {

}
