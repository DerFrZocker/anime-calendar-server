package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;

public record PreIgnoreTIDDataNotificationActionDeleteEvent(IgnoreTIDDataNotificationAction action,
                                                            RequestContext context) {

}
