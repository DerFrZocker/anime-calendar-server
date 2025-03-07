package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;

public record PreNotificationDeleteEvent(Notification notification, RequestContext context) {

}
