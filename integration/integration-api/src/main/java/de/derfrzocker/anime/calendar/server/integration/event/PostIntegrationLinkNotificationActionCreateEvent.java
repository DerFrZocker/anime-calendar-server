package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;

public record PostIntegrationLinkNotificationActionCreateEvent(IntegrationLinkNotificationAction action,
                                                               IntegrationLinkNotificationActionCreateData createData,
                                                               RequestContext context) {

}
