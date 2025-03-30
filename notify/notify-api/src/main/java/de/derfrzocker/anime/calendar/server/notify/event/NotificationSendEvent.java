package de.derfrzocker.anime.calendar.server.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import java.util.List;

public record NotificationSendEvent(Notification notification, List<NotificationAction> actions,
                                    RequestContext context) {

}
