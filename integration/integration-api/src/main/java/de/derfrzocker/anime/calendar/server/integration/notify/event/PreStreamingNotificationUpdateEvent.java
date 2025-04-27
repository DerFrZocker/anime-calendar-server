package de.derfrzocker.anime.calendar.server.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationUpdateData;

public record PreStreamingNotificationUpdateEvent(StreamingNotification current, StreamingNotification updated,
                                                  StreamingNotificationUpdateData updateData, RequestContext context) {

}
