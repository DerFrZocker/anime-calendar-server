package de.derfrzocker.anime.calendar.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.Notification;
import de.derfrzocker.anime.calendar.notify.api.NotificationUpdateData;

public record PostNotificationUpdateEvent(Notification current, Notification updated, NotificationUpdateData updateData,
                                          RequestContext context) {

}
