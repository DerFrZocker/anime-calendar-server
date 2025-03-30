package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;

public record PreNotificationActionDeleteEvent(NotificationAction notificationAction, RequestContext context) {

}
