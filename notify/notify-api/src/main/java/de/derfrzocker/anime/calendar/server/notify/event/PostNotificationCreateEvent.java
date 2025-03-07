package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;

public record PostNotificationCreateEvent(Notification notification, NotificationCreateData createData,
                                          RequestContext context) {

}
