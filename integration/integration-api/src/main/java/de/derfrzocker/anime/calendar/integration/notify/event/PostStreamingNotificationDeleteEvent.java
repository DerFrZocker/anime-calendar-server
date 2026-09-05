package de.derfrzocker.anime.calendar.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotification;

public record PostStreamingNotificationDeleteEvent(StreamingNotification notification, RequestContext context) {

}
