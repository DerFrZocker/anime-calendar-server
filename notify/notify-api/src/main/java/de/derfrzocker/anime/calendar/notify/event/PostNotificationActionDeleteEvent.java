package de.derfrzocker.anime.calendar.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;

public record PostNotificationActionDeleteEvent(NotificationAction notificationAction, RequestContext context) {

}
