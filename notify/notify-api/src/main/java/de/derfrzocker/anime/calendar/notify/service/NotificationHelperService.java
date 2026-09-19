package de.derfrzocker.anime.calendar.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;

public interface NotificationHelperService {

    void send(NotificationId id, RequestContext context);
}
