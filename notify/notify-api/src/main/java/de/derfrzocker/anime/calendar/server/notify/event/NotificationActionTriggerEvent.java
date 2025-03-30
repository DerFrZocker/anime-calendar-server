package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;

public record NotificationActionTriggerEvent(Notification notification, NotificationAction action,
                                             RequestContext context) {

}
