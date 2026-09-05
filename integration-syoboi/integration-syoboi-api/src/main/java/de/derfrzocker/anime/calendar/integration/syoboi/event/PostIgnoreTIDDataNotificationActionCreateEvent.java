package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;

public record PostIgnoreTIDDataNotificationActionCreateEvent(IgnoreTIDDataNotificationAction action,
                                                             IgnoreTIDDataNotificationActionCreateData createData,
                                                             RequestContext context) {

}
