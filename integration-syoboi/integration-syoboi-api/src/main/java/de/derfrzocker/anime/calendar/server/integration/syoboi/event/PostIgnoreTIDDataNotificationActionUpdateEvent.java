package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionUpdateData;

public record PostIgnoreTIDDataNotificationActionUpdateEvent(IgnoreTIDDataNotificationAction current,
                                                             IgnoreTIDDataNotificationAction updated,
                                                             IgnoreTIDDataNotificationActionUpdateData updateData,
                                                             RequestContext context) {

}
