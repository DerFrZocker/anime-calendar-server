package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;

public record PreNotificationCreateEvent(Notification notification, NotificationCreateData createData,
                                         RequestContext context) {

}
