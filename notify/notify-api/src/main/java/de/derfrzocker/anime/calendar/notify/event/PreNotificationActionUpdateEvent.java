package de.derfrzocker.anime.calendar.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.notify.api.NotificationActionUpdateData;

public record PreNotificationActionUpdateEvent(NotificationAction current, NotificationAction updated,
                                               NotificationActionUpdateData updateData, RequestContext context) {

}
