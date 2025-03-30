package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;

public record PostNotificationActionCreateEvent(NotificationAction notificationAction,
                                                NotificationActionCreateData createData, RequestContext context) {

}
