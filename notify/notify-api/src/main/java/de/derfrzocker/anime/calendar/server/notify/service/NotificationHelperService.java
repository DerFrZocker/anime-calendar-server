package de.derfrzocker.anime.calendar.server.notify.service;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;

public interface NotificationHelperService {

    void send(NotificationId id, RequestContext context);

    void execute(NotificationActionId id, RequestContext context);
}
