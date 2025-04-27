package de.derfrzocker.anime.calendar.server.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;

public record PreStreamingNotificationActionDeleteEvent(StreamingNotificationAction action, RequestContext context) {

}
