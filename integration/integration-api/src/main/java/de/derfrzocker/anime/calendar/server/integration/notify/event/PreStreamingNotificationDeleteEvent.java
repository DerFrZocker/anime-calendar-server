package de.derfrzocker.anime.calendar.server.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;

public record PreStreamingNotificationDeleteEvent(StreamingNotification notification, RequestContext context) {

}
