package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;

public record PostIntegrationLinkNotificationActionDeleteEvent(IntegrationLinkNotificationAction action,
                                                               RequestContext context) {

}
