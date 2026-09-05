package de.derfrzocker.anime.calendar.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationUpdateData;

public record PostStreamingNotificationUpdateEvent(StreamingNotification current, StreamingNotification updated,
                                                   StreamingNotificationUpdateData updateData, RequestContext context) {

}
