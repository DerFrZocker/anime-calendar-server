package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;

public record PostNotificationActionDeleteEvent(NotificationAction notificationAction, RequestContext context) {

}
