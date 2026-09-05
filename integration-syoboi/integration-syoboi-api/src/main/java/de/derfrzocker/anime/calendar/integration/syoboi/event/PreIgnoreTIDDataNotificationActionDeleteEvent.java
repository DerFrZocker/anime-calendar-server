package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationAction;

public record PreIgnoreTIDDataNotificationActionDeleteEvent(IgnoreTIDDataNotificationAction action,
                                                            RequestContext context) {

}
