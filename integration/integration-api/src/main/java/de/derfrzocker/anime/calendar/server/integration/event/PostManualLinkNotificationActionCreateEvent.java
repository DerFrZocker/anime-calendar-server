package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionCreateData;

public record PostManualLinkNotificationActionCreateEvent(ManualLinkNotificationAction action,
                                                          ManualLinkNotificationActionCreateData createData,
                                                          RequestContext context) {

}
