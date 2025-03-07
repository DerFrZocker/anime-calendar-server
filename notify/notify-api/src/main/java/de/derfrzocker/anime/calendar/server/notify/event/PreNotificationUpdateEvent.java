package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationUpdateData;

public record PreNotificationUpdateEvent(Notification current, Notification updated, NotificationUpdateData updateData,
                                         RequestContext context) {

}
