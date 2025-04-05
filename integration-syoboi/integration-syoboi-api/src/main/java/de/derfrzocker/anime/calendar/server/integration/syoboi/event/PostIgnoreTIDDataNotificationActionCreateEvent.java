package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;

public record PostIgnoreTIDDataNotificationActionCreateEvent(IgnoreTIDDataNotificationAction action,
                                                             IgnoreTIDDataNotificationActionCreateData createData,
                                                             RequestContext context) {

}
