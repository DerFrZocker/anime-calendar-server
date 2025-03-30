package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationUpdateData;

public record PostNotificationUpdateEvent(Notification current, Notification updated, NotificationUpdateData updateData,
                                          RequestContext context) {

}
