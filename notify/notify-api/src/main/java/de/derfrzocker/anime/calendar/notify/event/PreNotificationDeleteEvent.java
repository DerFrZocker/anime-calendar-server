package de.derfrzocker.anime.calendar.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.Notification;

public record PreNotificationDeleteEvent(Notification notification, RequestContext context) {

}
