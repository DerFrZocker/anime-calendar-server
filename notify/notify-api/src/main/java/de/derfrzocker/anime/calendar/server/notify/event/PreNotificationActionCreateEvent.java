package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;

public record PreNotificationActionCreateEvent(NotificationAction notificationAction,
                                               NotificationActionCreateData createData, RequestContext context) {

}
