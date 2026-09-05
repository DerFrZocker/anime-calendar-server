package de.derfrzocker.anime.calendar.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationAction;

public record PostStreamingNotificationActionDeleteEvent(StreamingNotificationAction action, RequestContext context) {

}
