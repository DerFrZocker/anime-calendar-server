package de.derfrzocker.anime.calendar.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.notify.api.NotificationActionCreateData;

public record PreNotificationActionCreateEvent(NotificationAction notificationAction,
                                               NotificationActionCreateData createData, RequestContext context) {

}
