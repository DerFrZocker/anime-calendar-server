package de.derfrzocker.anime.calendar.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.Notification;
import de.derfrzocker.anime.calendar.notify.api.NotificationCreateData;

public record PostNotificationCreateEvent(Notification notification, NotificationCreateData createData,
                                          RequestContext context) {

}
