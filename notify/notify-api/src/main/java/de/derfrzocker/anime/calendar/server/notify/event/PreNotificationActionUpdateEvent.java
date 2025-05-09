package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionUpdateData;

public record PreNotificationActionUpdateEvent(NotificationAction current, NotificationAction updated,
                                               NotificationActionUpdateData updateData, RequestContext context) {

}
